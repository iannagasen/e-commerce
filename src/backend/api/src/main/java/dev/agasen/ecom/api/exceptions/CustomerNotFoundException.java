package dev.agasen.ecom.api.exceptions;

public class CustomerNotFoundException extends RuntimeException {

  public static final String MESSAGE = "Customer was not found";

  public CustomerNotFoundException() {
    super(MESSAGE);
  }
  
}
