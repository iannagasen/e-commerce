package dev.agasen.ecom.product;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import dev.agasen.ecom.api.core.product.model.Product;
import dev.agasen.ecom.product.persistence.ProductEntity;
import dev.agasen.ecom.product.persistence.ProductRepository;
import dev.agasen.ecom.util.mongo.SequenceGeneratorService;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {
  
  @Mock
  private ProductRepository productRepository;

  @Mock
  private SequenceGeneratorService sequence; 

  @InjectMocks
  private ProductService productService;

  @Test
  public void ProductService_createProduct_returnsProduct() {
    
    ProductEntity entity = new ProductEntity(1, "productname", "a description");
    Product product = new Product(1, "name", "a description");

    when(sequence.generateSequence(ProductEntity.SEQUENCE_NAME)).thenReturn(Mono.just(1L));
    when(productRepository.save(any(ProductEntity.class))).thenReturn(Mono.just(entity));
    when(productRepository.toProductEntityModel(product)).thenReturn(entity);
    when(productRepository.toProductRestModel(entity)).thenReturn(product);

    Mono<Product> savedProduct = productService.createProduct(product);

    StepVerifier.create(savedProduct)
                .expectNext(product)
                .verifyComplete();
  }

}
