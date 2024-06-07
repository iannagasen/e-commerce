package dev.agasen.ecom.inventory;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RestController;

import dev.agasen.ecom.api.core.inventory.event.InventoryEventService;
import dev.agasen.ecom.api.core.inventory.model.Inventory;
import dev.agasen.ecom.api.core.inventory.model.InventoryDeductionRequest;
import dev.agasen.ecom.api.core.inventory.model.InventoryUpdate;
import dev.agasen.ecom.api.core.inventory.model.InventoryUpdateType;
import dev.agasen.ecom.api.core.inventory.rest.InventoryRestService;
import dev.agasen.ecom.api.exceptions.InsufficientStockException;
import dev.agasen.ecom.api.exceptions.ProductNotFoundException;
import dev.agasen.ecom.inventory.persistence.InventoryEntity;
import dev.agasen.ecom.inventory.persistence.InventoryRepository;
import dev.agasen.ecom.inventory.persistence.InventoryUpdateEntity;
import dev.agasen.ecom.inventory.persistence.InventoryUpdateRepository;
import dev.agasen.ecom.inventory.rest.InventoryRestMappers;
import dev.agasen.ecom.util.mongo.SequenceGeneratorService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;

@RestController
@Slf4j
@RequiredArgsConstructor
public class InventoryService implements InventoryRestService, InventoryEventService {

  private final InventoryRepository inventoryRepo;
  private final InventoryUpdateRepository inventoryHistoryRepo;
  private final SequenceGeneratorService sequenceGenerator;

  private static final Mono<InventoryEntity> PRODUCT_NOTFOUND = Mono.error(new ProductNotFoundException());
  private static final Mono<InventoryEntity> INSUFFICIENT_STOCK = Mono.error(new InsufficientStockException());
  
  @Override
  public Mono<Inventory> deduct(InventoryDeductionRequest request) {
    return inventoryRepo.findByProductId(request.productId())
        .switchIfEmpty(PRODUCT_NOTFOUND)
        .filter(i -> i.getStock() >= request.quantity())
        .switchIfEmpty(INSUFFICIENT_STOCK)
        .flatMap(i ->  doDeduct(i, request))
        .zipWhen(i -> inventoryHistoryRepo.findAllByProductId(request.productId()).collectList())
        .map(tuple -> InventoryRestMappers.map(tuple.getT1(), tuple.getT2()));
  }

  @Transactional
  private Mono<InventoryEntity> doDeduct(InventoryEntity inventory, InventoryDeductionRequest req) {
    var inventoryUpdate = new InventoryUpdateEntity(req.productId(), req.orderId(), InventoryUpdateType.PURCHASE, req.quantity());
    return sequenceGenerator.generateSequence(InventoryEntity.SEQUENCE_NAME)
        .map(inventory::withInventoryId)
        .flatMap(i -> inventoryRepo.deductInventory(i, req.quantity()))
        .zipWhen(i -> inventoryHistoryRepo.save(inventoryUpdate))
        .map(Tuple2::getT1)
        .doOnNext(i -> log.info("Inventory Update successful with orderId: {}", req.orderId()));
  }

  @Override
  public Mono<Inventory> restore(Long orderId) {
    throw new UnsupportedOperationException("Unimplemented method 'restore'");
  }

  @Override
  public Mono<Inventory> getInventory(Long productId) {
    return inventoryRepo
      .findByProductId(productId)
      .map(i -> InventoryRestMappers.map(null));
  }

  @Override
  public Flux<Inventory> getInventories() {
    return inventoryRepo
      .findAll()
      .map(i -> InventoryRestMappers.map(null));
  }

  @Override
  public Flux<InventoryUpdate> getInventoryHistory(Long productId) {
    return inventoryHistoryRepo.findAllByProductId(productId);
  }

  
}