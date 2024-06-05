package dev.agasen.ecom.api.core.payment.model;

import lombok.Builder;

@Builder
public record PaymentProcessRequest(Long customerId, Long orderId, Long amount) {

}
