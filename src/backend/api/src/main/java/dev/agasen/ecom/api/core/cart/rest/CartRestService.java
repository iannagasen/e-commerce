package dev.agasen.ecom.api.core.cart.rest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import dev.agasen.ecom.api.core.cart.model.Cart;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface CartRestService {
  
  @GetMapping("/cart/{cartId}")
  Mono<Cart> getCart(@PathVariable Long cartId);

  @GetMapping("/cart")
  Flux<Cart> getCarts();
   
}