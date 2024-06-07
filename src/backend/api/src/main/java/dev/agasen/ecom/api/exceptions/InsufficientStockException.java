package dev.agasen.ecom.api.exceptions;

public class InsufficientStockException extends RuntimeException {
  
  public static final String MESSAGE = "Trying to purchase a product with quantity bigger than the current product stock.";

  public InsufficientStockException() {
    super(MESSAGE);
  }

}
