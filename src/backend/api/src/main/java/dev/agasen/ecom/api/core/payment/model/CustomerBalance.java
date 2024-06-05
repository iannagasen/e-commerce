package dev.agasen.ecom.api.core.payment.model;

import lombok.Builder;

@Builder
public record CustomerBalance(String id, Long userId, String customerName, Long balance) { }
