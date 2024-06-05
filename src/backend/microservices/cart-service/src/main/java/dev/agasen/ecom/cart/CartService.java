package dev.agasen.ecom.cart;

import org.springframework.web.bind.annotation.RestController;

import dev.agasen.ecom.api.core.cart.model.Cart;
import dev.agasen.ecom.api.core.cart.rest.CartRestService;
import dev.agasen.ecom.cart.persistence.CartRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
public class CartService implements CartRestService {

  private final CartRepository cartRepository;

  public @Override Mono<Cart> getCart(Long cartId) {
    return cartRepository
        .getCartByCartId(cartId)
        .map(cartRepository::toRestModel);
  }

  public @Override Flux<Cart> getCarts() {
    return cartRepository
        .findAll()
        .map(cartRepository::toRestModel);
  }
  
}
