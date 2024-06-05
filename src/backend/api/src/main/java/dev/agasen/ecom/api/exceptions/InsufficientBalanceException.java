package dev.agasen.ecom.api.exceptions;

public class InsufficientBalanceException extends RuntimeException {

  public InsufficientBalanceException() {
    super("Insufficent balance.");
  }
  
}
