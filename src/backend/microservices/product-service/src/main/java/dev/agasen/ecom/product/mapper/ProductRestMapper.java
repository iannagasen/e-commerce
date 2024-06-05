package dev.agasen.ecom.product.mapper;

import dev.agasen.ecom.api.core.product.model.Product;
import dev.agasen.ecom.product.persistence.ProductEntity;

public record ProductRestMapper() {
  public static Product toRest(ProductEntity entity) {
    return new Product(entity.getProductId(), entity.getName(), entity.getDescription());
  }

  public static ProductEntity toEntity(Product rest) {
    return new ProductEntity(rest.id(), rest.name(), rest.description());
  }

  public static ProductEntity toEntity(Product rest, Long productId) {
    return new ProductEntity(productId, rest.name(), rest.description());
  } 
}
