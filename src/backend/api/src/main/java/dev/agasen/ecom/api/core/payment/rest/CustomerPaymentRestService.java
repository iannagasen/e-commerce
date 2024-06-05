package dev.agasen.ecom.api.core.payment.rest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import dev.agasen.ecom.api.core.payment.model.CustomerBalance;
import dev.agasen.ecom.api.core.payment.model.CustomerPayment;
import dev.agasen.ecom.api.core.payment.model.PaymentProcessRequest;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import reactor.core.publisher.Mono;

public interface CustomerPaymentRestService {
  
  @GetMapping("/payment/{orderId}")
  Mono<CustomerPayment> getCustomerPayment(@PathVariable Long orderId);

  @GetMapping("/balance/{userId}")
  Mono<CustomerBalance> getCustomerBalance(@PathVariable Long userId);

  @PostMapping("/payment")
  Mono<CustomerPayment> postProcessPayment(@RequestBody PaymentProcessRequest request);

  @PostMapping("/payment/{orderId}/refund")
  Mono<CustomerPayment> postRefundPayment(@PathVariable Long orderId);
  
}
