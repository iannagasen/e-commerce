package dev.agasen.ecom.payment.messaging;

import java.time.Instant;
import java.util.function.Function;

import org.springframework.stereotype.Component;

import dev.agasen.ecom.api.core.order.event.OrderEvent;
import dev.agasen.ecom.api.core.payment.event.PaymentEvent;
import dev.agasen.ecom.api.core.payment.model.CustomerPayment;
import dev.agasen.ecom.api.core.payment.model.PaymentProcessRequest;
import reactor.core.publisher.Mono;


public record CustomerPaymentMessageMapper() {
  
  public static PaymentProcessRequest toPaymentProcessRequest(OrderEvent.Created event) {
    return PaymentProcessRequest.builder()
        .customerId(event.customerId())
        .orderId(event.orderId())
        .amount(event.totalAmount())
        .build();
  }

  public static PaymentEvent toPaymentDeductedEvent(CustomerPayment p) {
    return PaymentEvent.Deducted.builder()
        .paymentId(p.id())
        .orderId(p.orderId())
        .amount(p.amount())
        .customerId(p.customerId())
        .createdAt(Instant.now())
        .build();
  }

  public static Function<Throwable, Mono<PaymentEvent>> paymentDeclinedEventMapper(OrderEvent.Created e) {
    return exc -> Mono.fromSupplier(
        () -> PaymentEvent.Declined.builder()
            .orderId(e.orderId())
            .amount(e.totalAmount())
            .customerId(e.customerId())
            .createdAt(Instant.now())
            .message(exc.getMessage())
            .build()
    );
  }

  public static PaymentEvent toPaymentRefundedEvent(CustomerPayment p) {
    return PaymentEvent.Refunded.builder()
        .paymentId(p.id())
        .orderId(p.orderId())
        .amount(p.amount())
        .customerId(p.customerId())
        .createdAt(Instant.now())
        .build();
  }

}
