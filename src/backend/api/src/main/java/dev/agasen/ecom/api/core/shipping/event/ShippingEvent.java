package dev.agasen.ecom.api.core.shipping.event;

import java.time.Instant;

import dev.agasen.ecom.api.event.DomainEvent;
import dev.agasen.ecom.api.event.OrderSaga;
import lombok.Builder;

public interface ShippingEvent extends DomainEvent, OrderSaga {
  
  @Builder
  record Scheduled(Long orderId,
                   Long shipmentId,
                   Instant expectedDelivery,
                   Instant createdAt) implements ShippingEvent{};

}
