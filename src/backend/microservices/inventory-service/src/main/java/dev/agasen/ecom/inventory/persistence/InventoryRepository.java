package dev.agasen.ecom.inventory.persistence;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import reactor.core.publisher.Mono;

public interface InventoryRepository extends ReactiveMongoRepository<InventoryEntity, String> {

  Mono<InventoryEntity> findByProductId(Long productId);

  default Mono<InventoryEntity> deductInventory(InventoryEntity inventory, int quantity) {
    inventory.setStock(inventory.getStock() - quantity);
    return save(inventory);
  }

}