package dev.agasen.ecom.api.core.order.event;

import dev.agasen.ecom.api.event.DomainEvent;
import dev.agasen.ecom.api.event.EventProcessor;
import reactor.core.publisher.Mono;

public interface OrderEventProcessor<R extends DomainEvent> extends EventProcessor<OrderEvent, R> {

  @Override
  default Mono<R> process(OrderEvent event) {
    return switch(event) {
      case OrderEvent.Cancelled e -> this.handle(e);
      case OrderEvent.Completted e -> this.handle(e);
      case OrderEvent.Created e -> this.handle(e);
      default -> throw new RuntimeException("This would never throw");
    };
  }

  Mono<R> handle(OrderEvent.Cancelled e);

  Mono<R> handle(OrderEvent.Completted e);

  Mono<R> handle(OrderEvent.Created e);

  
}
