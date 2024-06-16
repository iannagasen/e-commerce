package dev.agasen.ecom.order.messaging.participants;

import dev.agasen.ecom.api.core.inventory.event.InventoryEvent.Declined;
import dev.agasen.ecom.api.core.inventory.event.InventoryEvent.Deducted;
import dev.agasen.ecom.api.core.inventory.event.InventoryEvent.Restored;

import org.springframework.stereotype.Component;

import dev.agasen.ecom.api.core.inventory.event.InventoryEventProcessor;
import dev.agasen.ecom.api.saga.order.event.OrderEvent;
import dev.agasen.ecom.api.saga.order.event.OrderFulfillmentAbstractService;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class InventoryOrderProcessor implements InventoryEventProcessor<OrderEvent> {
  
  private final OrderFulfillmentAbstractService fulfillmentService;

  @Override
  public Mono<OrderEvent> handle(Deducted e) {
    throw new UnsupportedOperationException("Unimplemented method 'handle'");
  }
  
  @Override
  public Mono<OrderEvent> handle(Declined e) {
    throw new UnsupportedOperationException("Unimplemented method 'handle'");
  }

  @Override
  public Mono<OrderEvent> handle(Restored e) {
    throw new UnsupportedOperationException("Unimplemented method 'handle'");
  }
  
}
