package dev.agasen.ecom.inventory.messaging;

import org.springframework.stereotype.Component;

import dev.agasen.ecom.api.core.inventory.event.InventoryEvent;
import dev.agasen.ecom.api.core.inventory.event.InventoryEventService;
import dev.agasen.ecom.api.core.inventory.model.Inventory;
import dev.agasen.ecom.api.saga.order.event.OrderEventProcessor;
import dev.agasen.ecom.api.saga.order.event.OrderEvent.Cancelled;
import dev.agasen.ecom.api.saga.order.event.OrderEvent.Completted;
import dev.agasen.ecom.api.saga.order.event.OrderEvent.Created;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class OrderInventoryEventProcessor implements OrderEventProcessor<InventoryEvent> {

  private final InventoryEventService eventService;

  @Override
  public Mono<InventoryEvent> handle(Created event) {
    // eventService.deduct(InventoryMessageMappers.toInventoryDeductionRequest(event))
    throw new UnsupportedOperationException("Unimplemented method 'handle'");
  }

  @Override
  public Mono<InventoryEvent> handle(Cancelled event) {
    throw new UnsupportedOperationException("Unimplemented method 'handle'");
  }

  @Override
  public Mono<InventoryEvent> handle(Completted event) {
    throw new UnsupportedOperationException("Unimplemented method 'handle'");
  }

  
}
