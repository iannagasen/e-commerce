package dev.agasen.ecom.api.core.payment.event;

import dev.agasen.ecom.api.core.payment.model.CustomerPayment;
import dev.agasen.ecom.api.core.payment.model.PaymentProcessRequest;
import reactor.core.publisher.Mono;

public interface CustomerPaymentEventService {
  
  Mono<CustomerPayment> process(PaymentProcessRequest request);

  Mono<CustomerPayment> refund(Long orderId);
  
}