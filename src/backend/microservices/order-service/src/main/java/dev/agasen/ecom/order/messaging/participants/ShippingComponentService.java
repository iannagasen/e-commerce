package dev.agasen.ecom.order.messaging.participants;

import org.springframework.stereotype.Service;

import dev.agasen.ecom.api.saga.order.event.OrderComponentFetcher;
import dev.agasen.ecom.api.saga.order.event.OrderComponentListener;
import dev.agasen.ecom.api.saga.order.model.OrderComponent.OrderShipment;
import dev.agasen.ecom.order.persistence.OrderComponentRepository;
import dev.agasen.ecom.order.util.OrderMapper;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class ShippingComponentService implements OrderComponentFetcher.Shipping, OrderComponentListener.Shipping {

  private final OrderComponentRepository repo;

  @Override
  public Mono<OrderShipment> getComponent(Long sagaId) {
    return repo.findOrderShipmentByOrderId(sagaId).map(OrderMapper::toOrderShipment);
  }

  @Override
  public Mono<Void> onSuccess(OrderShipment message) {
    return repo.findOrderShipmentByOrderId(message.orderId())
        .switchIfEmpty(Mono.defer(() -> {
            var entity = OrderMapper.toOrderShippingEntity(message);
            entity.setSuccessful(true);
            return repo.save(entity);
        }))
        .then();
  }

  @Override
  public Mono<Void> onFailure(OrderShipment message) {
    return repo.findOrderShipmentByOrderId(message.orderId())
        .switchIfEmpty(Mono.defer(() -> {
            var entity = OrderMapper.toOrderShippingEntity(message);
            entity.setSuccessful(false);
            return repo.save(entity);
        }))
        .then();
  }

  @Override
  public Mono<Void> onRollback(OrderShipment message) {
    return repo.findOrderShipmentByOrderId(message.orderId())
        .doOnNext(e -> e.setStatus(message.status()))
        .flatMap(repo::save)
        .then();
  }


}