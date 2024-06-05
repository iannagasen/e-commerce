package dev.agasen.ecom.cart.persistence;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import dev.agasen.ecom.api.core.cart.model.Cart;
import reactor.core.publisher.Mono;

public interface CartRepository extends ReactiveMongoRepository<CartEntity, String> {

  Mono<CartEntity> getCartByCartId(Long cartId);

  default Cart toRestModel(CartEntity e) {
    return Cart.builder()
        .userId(0L) // ! temporary set user Id
        .cartId(e.getCartId())
        .items(e.getItems())
        .build();
  }
}