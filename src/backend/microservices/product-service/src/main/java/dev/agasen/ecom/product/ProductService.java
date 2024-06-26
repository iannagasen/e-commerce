package dev.agasen.ecom.product;

import org.springframework.web.bind.annotation.RestController;

import dev.agasen.ecom.api.core.product.model.Product;
import dev.agasen.ecom.api.core.product.rest.ProductRestService;
import dev.agasen.ecom.product.mapper.ProductRestMapper;
import dev.agasen.ecom.product.persistence.ProductEntity;
import dev.agasen.ecom.product.persistence.ProductRepository;
import dev.agasen.ecom.util.mongo.SequenceGeneratorService;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
public class ProductService implements ProductRestService {

  private final ProductRepository repository;
  private final SequenceGeneratorService sequence;

  public @Override Flux<Product> getProducts() {
    return repository.findAll().map(ProductRestMapper::toRest);
  }
  
  public @Override Mono<Product> getProduct(Long id) {
    throw new UnsupportedOperationException("Unimplemented method 'getProduct'");
  }
  
  public @Override Flux<Product> getProductsByCategory(String category) {
    throw new UnsupportedOperationException("Unimplemented method 'getProductsByCategory'");
  }
  
  public @Override Mono<Product> createProduct(Product product) {
    return sequence
        .generateSequence(ProductEntity.SEQUENCE_NAME)
        .map(seq -> ProductRestMapper.toEntity(product, seq))
        .flatMap(repository::save)
        .map(ProductRestMapper::toRest);
  }
}
