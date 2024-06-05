package dev.agasen.ecom.api.core.payment.event;

import dev.agasen.ecom.api.core.payment.event.PaymentEvent.Declined;
import dev.agasen.ecom.api.core.payment.event.PaymentEvent.Deducted;
import dev.agasen.ecom.api.core.payment.event.PaymentEvent.Refunded;
import dev.agasen.ecom.api.event.DomainEvent;
import dev.agasen.ecom.api.event.EventProcessor;
import reactor.core.publisher.Mono;

public interface PaymentEventProcessor<R extends DomainEvent> extends EventProcessor<PaymentEvent, R>{

  @Override
  default Mono<R> process(PaymentEvent event) {
    return switch(event) {
      case PaymentEvent.Declined e -> this.handle(e);
      case PaymentEvent.Deducted e -> this.handle(e);
      case PaymentEvent.Refunded e -> this.handle(e);
      default -> throw new IllegalArgumentException("would never happen");
    };
  }

  Mono<R> handle(Refunded e);

  Mono<R> handle(Deducted e);

  Mono<R> handle(Declined e);
  
}
