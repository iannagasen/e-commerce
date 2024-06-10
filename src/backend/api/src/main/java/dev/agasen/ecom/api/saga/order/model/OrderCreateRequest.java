package dev.agasen.ecom.api.saga.order.model;

import lombok.Builder;

@Builder
public record OrderCreateRequest(Long customerId,
                                 Long productId,
                                 int quantity,
                                 int unitPrice) { }
