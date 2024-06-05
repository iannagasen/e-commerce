package dev.agasen.ecom.api.core.payment.model;

import dev.agasen.ecom.api.core.payment.event.PaymentStatus;
import lombok.Builder;

@Builder
public record CustomerPayment(
  String id, 
  Long orderId, 
  Long customerId, 
  PaymentStatus status, 
  Long amount
) {}
