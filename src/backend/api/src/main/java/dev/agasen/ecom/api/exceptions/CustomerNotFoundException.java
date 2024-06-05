package dev.agasen.ecom.api.exceptions;

public class CustomerNotFoundException extends RuntimeException {

  public CustomerNotFoundException() {
    super("Customer was not found");
  }
  
}
