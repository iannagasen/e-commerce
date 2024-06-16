package dev.agasen.ecom.order.messaging.participants;

import org.springframework.stereotype.Service;

import dev.agasen.ecom.api.saga.order.event.OrderComponentFetcher;
import dev.agasen.ecom.api.saga.order.event.OrderComponentListener;
import dev.agasen.ecom.api.saga.order.model.OrderComponent.OrderInventory;
import dev.agasen.ecom.order.persistence.OrderComponentRepository;
import dev.agasen.ecom.order.util.OrderMapper;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class InventoryComponentService implements OrderComponentFetcher.Inventory, OrderComponentListener.Inventory{

  private final OrderComponentRepository repo;

  @Override
  public Mono<OrderInventory> getComponent(Long sagaId) {
    return this.repo.findOrderInventoryByOrderId(sagaId).map(OrderMapper::toOrderInventory);
  }

  @Override
  public Mono<Void> onSuccess(OrderInventory message) {
    return repo.findOrderInventoryByOrderId(message.orderId())
        .switchIfEmpty(Mono.defer(() -> {
            var entity = OrderMapper.toOrderInventoryEntity(message);
            entity.setSuccessful(true);
            return repo.save(entity);
        }))
        .then();
  }

  @Override
  public Mono<Void> onFailure(OrderInventory message) {
    return repo.findOrderInventoryByOrderId(message.orderId())
        .switchIfEmpty(Mono.defer(() -> {
            var entity = OrderMapper.toOrderInventoryEntity(message);
            entity.setSuccessful(false);
            return repo.save(entity);
        }))
        .then();

  }

  @Override
  public Mono<Void> onRollback(OrderInventory message) {
    return repo.findOrderInventoryByOrderId(message.orderId())
        .doOnNext(e -> e.setStatus(message.status()))
        .flatMap(repo::save)
        .then();
  }
  
}
