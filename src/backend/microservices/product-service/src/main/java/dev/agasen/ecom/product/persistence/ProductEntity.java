package dev.agasen.ecom.product.persistence;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Document(collection="products")
public class ProductEntity {

  public @Transient static final String SEQUENCE_NAME = "products_sequence";

  private @Id String id;
  private @Indexed(unique=true) int productId;
  private String name;
  private String description;

  public ProductEntity(int productId, String name, String description) {
    this.productId = productId;
    this.name = name;
    this.description = description;
  }
  
}
