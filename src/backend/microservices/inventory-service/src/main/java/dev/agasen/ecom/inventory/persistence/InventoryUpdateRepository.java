package dev.agasen.ecom.inventory.persistence;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import dev.agasen.ecom.api.core.inventory.model.InventoryUpdate;
import reactor.core.publisher.Flux;

public interface InventoryUpdateRepository extends ReactiveMongoRepository<InventoryUpdateEntity, String> {

  Flux<InventoryUpdateEntity> findAllByProductId(Long productId);

  default InventoryUpdate toRestModel(InventoryUpdateEntity e) {
    return new InventoryUpdate(e.getProductId(), e.getType(), e.getDateTime());
  }
}
