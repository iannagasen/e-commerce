package dev.agasen.ecom.product.persistence;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import dev.agasen.ecom.api.core.product.model.Product;

public interface ProductRepository extends ReactiveMongoRepository<ProductEntity, String> {

  default Product toProductRestModel(ProductEntity entity) {
    return new Product(entity.getProductId(), entity.getName(), entity.getDescription());
  }

  default ProductEntity toProductEntityModel(Product rest) {
    return new ProductEntity(rest.id(), rest.name(), rest.description());
  }
  
}