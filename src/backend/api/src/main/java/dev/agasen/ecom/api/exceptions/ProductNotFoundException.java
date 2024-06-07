package dev.agasen.ecom.api.exceptions;

public class ProductNotFoundException extends RuntimeException {
  
  public static final String MESSAGE = "Product not found.";

  public ProductNotFoundException() {
    super(MESSAGE);
  }

}
