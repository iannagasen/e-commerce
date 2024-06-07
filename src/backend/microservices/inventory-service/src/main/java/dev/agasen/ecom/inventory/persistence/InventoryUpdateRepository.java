package dev.agasen.ecom.inventory.persistence;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import dev.agasen.ecom.api.core.inventory.model.InventoryUpdate;
import reactor.core.publisher.Flux;

public interface InventoryUpdateRepository extends ReactiveMongoRepository<InventoryUpdateEntity, String> {

  Flux<InventoryUpdate> findAllByProductId(Long productId);

}
