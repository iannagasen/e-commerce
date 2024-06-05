package dev.agasen.ecom.api.core.shipping.event;

import dev.agasen.ecom.api.core.shipping.event.ShippingEvent.Scheduled;
import dev.agasen.ecom.api.event.DomainEvent;
import dev.agasen.ecom.api.event.EventProcessor;
import reactor.core.publisher.Mono;

public interface ShippingEventProcessor<R extends DomainEvent> extends EventProcessor<ShippingEvent, R>{

  @Override
  default Mono<R> process(ShippingEvent event) {
    return switch (event){
      case ShippingEvent.Scheduled e -> this.handle(e);
      default -> throw new IllegalArgumentException("would never happen");
    };
  }

  Mono<R> handle(Scheduled e);
  
}
