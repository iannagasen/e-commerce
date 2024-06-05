package dev.agasen.ecom.payment.persistence;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
@Document(collection="customer_balance")
public class CustomerBalanceEntity {
  
  private @Id String id;
  private @Indexed(unique=true) Long customerId;
  private String customerName;
  private Long balance;
  
}
