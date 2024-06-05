package dev.agasen.ecom.api.core.cart.model;

import java.util.List;

import lombok.Builder;

@Builder
public record Cart(
  Long cartId,
  Long userId,
  List<CartItem> items
) { }