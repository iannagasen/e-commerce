package dev.agasen.ecom.payment.persistence;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import dev.agasen.ecom.api.core.payment.event.PaymentStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
@Document(collection = "customer_payments")
public class CustomerPaymentEntity {

  private @Id String id;
  // ! do I need an explicit id for every entity
  // private @Indexed Long paymentId;
  private @Indexed(unique=true) Long orderId;
  private Long customerId;
  private PaymentStatus status;
  private Long amount;

}
