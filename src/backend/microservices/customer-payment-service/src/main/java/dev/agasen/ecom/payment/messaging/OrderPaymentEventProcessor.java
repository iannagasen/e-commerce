package dev.agasen.ecom.payment.messaging;

import java.util.function.UnaryOperator;

import org.springframework.stereotype.Component;

import dev.agasen.ecom.api.core.order.event.OrderEvent;
import dev.agasen.ecom.api.core.order.event.OrderEventProcessor;
import dev.agasen.ecom.api.core.order.event.OrderEvent.Cancelled;
import dev.agasen.ecom.api.core.order.event.OrderEvent.Completted;
import dev.agasen.ecom.api.core.order.event.OrderEvent.Created;
import dev.agasen.ecom.api.core.payment.event.CustomerPaymentEventService;
import dev.agasen.ecom.api.core.payment.event.PaymentEvent;
import dev.agasen.ecom.api.exceptions.EventAlreadyProcessedException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
@Slf4j
public class OrderPaymentEventProcessor implements OrderEventProcessor<PaymentEvent>{

  private final CustomerPaymentEventService paymentService;
  private final CustomerPaymentMessageMapper paymentMsgMapper;

  @Override
  public Mono<PaymentEvent> handle(Created event) {
    return paymentService
        .process(paymentMsgMapper.toPaymentProcessRequest(event))
        .map(paymentMsgMapper::toPaymentDeductedEvent)
        .doOnNext(e -> log.info("payment processed: {}", e))
        .transform(filterAndHandleExceptions(event));
  }

  @Override
  public Mono<PaymentEvent> handle(Cancelled event) {
    return paymentService.refund(event.orderId())
        .map(this.paymentMsgMapper::toPaymentRefundedEvent)
        .doOnNext(e -> log.info("payment refunded: {}", e))
        .doOnError(ex -> log.error("error while processing refund", ex));
  }

  @Override
  public Mono<PaymentEvent> handle(Completted event) {
    return Mono.empty();
  }

  /**
   * * Will act like a filter for exceptions,
   * * if no exceptions thrown, will return the same mono
   */
  private UnaryOperator<Mono<PaymentEvent>> filterAndHandleExceptions(OrderEvent.Created e) {
    return mono -> mono
        // * if Event is already process, just return an empty mono (wc will eventually cancel the pipeline)
        .onErrorResume(EventAlreadyProcessedException.class, exc -> Mono.empty())
        // * for every other exception, map to Mono<PaymentEvent> with message = exception
        .onErrorResume(paymentMsgMapper.paymentDeclinedEventMapper(e));
  } 
}
