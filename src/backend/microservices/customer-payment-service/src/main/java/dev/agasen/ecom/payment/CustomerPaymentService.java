package dev.agasen.ecom.payment;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RestController;

import dev.agasen.ecom.api.core.payment.event.CustomerPaymentEventService;
import dev.agasen.ecom.api.core.payment.event.PaymentStatus;
import dev.agasen.ecom.api.core.payment.model.CustomerBalance;
import dev.agasen.ecom.api.core.payment.model.CustomerPayment;
import dev.agasen.ecom.api.core.payment.model.PaymentProcessRequest;
import dev.agasen.ecom.api.core.payment.rest.CustomerPaymentRestService;
import dev.agasen.ecom.api.exceptions.CustomerNotFoundException;
import dev.agasen.ecom.api.exceptions.InsufficientBalanceException;
import dev.agasen.ecom.api.exceptions.PaymentNotFoundException;
import dev.agasen.ecom.api.exceptions.UndeductedPaymentRefundException;
import dev.agasen.ecom.payment.persistence.CustomerBalanceEntity;
import dev.agasen.ecom.payment.persistence.CustomerBalanceRepository;
import dev.agasen.ecom.payment.persistence.CustomerPaymentEntity;
import dev.agasen.ecom.payment.persistence.CustomerPaymentRepository;
import dev.agasen.ecom.payment.rest.CustomerPaymentMapper;
import dev.agasen.ecom.util.event.DuplicateEventValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@Slf4j
public class CustomerPaymentService implements CustomerPaymentEventService, CustomerPaymentRestService {

  private final CustomerPaymentRepository paymentRepo;
  private final CustomerBalanceRepository balanceRepo;
  private final CustomerPaymentMapper paymentMapper;
  private final DuplicateEventValidator duplicateEventValidator;

  private static final Mono<CustomerBalanceEntity> CUSTOMER_NOT_FOUND = Mono.error(new CustomerNotFoundException());
  private static final Mono<CustomerBalanceEntity> INSUFFICIENT_BALANCE = Mono.error(new InsufficientBalanceException());
  private static final Mono<CustomerPaymentEntity> PAYMENT_NOT_FOUND = Mono.error(new PaymentNotFoundException());
  private static final Mono<CustomerPaymentEntity> CANT_REFUND_UNDEDUCTED_PAYMENT = Mono.error(new UndeductedPaymentRefundException());

  @Override
  public Mono<CustomerPayment> process(PaymentProcessRequest request) {
    return duplicateEventValidator.validate(
        this.paymentRepo.existsByOrderId(request.orderId()),
        this.balanceRepo.findByCustomerId(request.customerId())
    )
        .switchIfEmpty(CUSTOMER_NOT_FOUND)
        .filter(c -> c.getBalance() >= request.amount())
        .switchIfEmpty(INSUFFICIENT_BALANCE)
        .flatMap(balance -> doProcessPayment(balance, request))
        .doOnNext(paymentEntity -> log.info("Payment processed for {}", paymentEntity.getOrderId()))
        .map(paymentMapper::toRestModel);
  }
  
  @Override
  public Mono<CustomerPayment> refund(Long orderId) {
    return paymentRepo.findByOrderId(orderId)
    .switchIfEmpty(PAYMENT_NOT_FOUND)
    .filter(payment -> PaymentStatus.DEDUCTED.equals(payment.getStatus()))
    .switchIfEmpty(CANT_REFUND_UNDEDUCTED_PAYMENT)
    .zipWhen(payment -> balanceRepo.findByCustomerId(payment.getCustomerId()))
    .flatMap(tuple -> doRefund(tuple.getT2(), tuple.getT1()))
    .doOnNext(paymentEntity -> log.info("Payment processed for {}", paymentEntity.getOrderId()))
    .map(paymentMapper::toRestModel);
  }

  @Transactional
  private Mono<CustomerPaymentEntity> doProcessPayment(CustomerBalanceEntity balance, PaymentProcessRequest paymentRequest) {
    CustomerPaymentEntity payment = paymentMapper.toEntityModel(paymentRequest);
    return balanceRepo.setBalance(balance, balance.getBalance() - payment.getAmount()).then(
           paymentRepo.setPaymentStatus(payment, PaymentStatus.DEDUCTED));
  }

  @Transactional
  private Mono<CustomerPaymentEntity> doRefund(CustomerBalanceEntity balance, CustomerPaymentEntity payment) {
    return balanceRepo.setBalance(balance, balance.getBalance() + payment.getAmount()).then(
           paymentRepo.setPaymentStatus(payment, PaymentStatus.REFUNDED));
  }

  @Override
  public Mono<CustomerPayment> getCustomerPayment(Long orderId) {
    throw new UnsupportedOperationException("Unimplemented method 'getCustomerPayment'");
  }

  @Override
  public Mono<CustomerBalance> getCustomerBalance(Long userId) {
    throw new UnsupportedOperationException("Unimplemented method 'getCustomerBalance'");
  }

  @Override
  public Mono<CustomerPayment> postProcessPayment(PaymentProcessRequest request) {
    return this.process(request);
  }

  @Override
  public Mono<CustomerPayment> postRefundPayment(Long orderId) {
    return this.refund(orderId);
  }
}
