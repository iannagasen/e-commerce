package dev.agasen.ecom.api.exceptions;

public class UndeductedPaymentRefundException extends RuntimeException {

  public UndeductedPaymentRefundException() {
    super("Cant refund payment when status is not yet deducted.");
  }
  
}
