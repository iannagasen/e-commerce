package dev.agasen.ecom.api.core.inventory.event;

import dev.agasen.ecom.api.core.inventory.event.InventoryEvent.Declined;
import dev.agasen.ecom.api.core.inventory.event.InventoryEvent.Deducted;
import dev.agasen.ecom.api.core.inventory.event.InventoryEvent.Restored;
import dev.agasen.ecom.api.event.DomainEvent;
import dev.agasen.ecom.api.event.EventProcessor;
import reactor.core.publisher.Mono;

public interface InventoryEventProcessor<R extends DomainEvent> extends EventProcessor<InventoryEvent, R>{
  
  @Override
  default Mono<R> process(InventoryEvent event) {
    return switch(event) {
      case InventoryEvent.Declined e -> this.handle(e);
      case InventoryEvent.Deducted e -> this.handle(e);
      case InventoryEvent.Restored e -> this.handle(e);
      default -> throw new IllegalArgumentException("should never happen");
    };
  }

  Mono<R> handle(Restored e);

  Mono<R> handle(Deducted e);

  Mono<R> handle(Declined e);
}
