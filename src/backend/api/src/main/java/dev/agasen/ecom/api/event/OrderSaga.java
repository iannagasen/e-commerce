package dev.agasen.ecom.api.event;

public interface OrderSaga extends Saga {

  Long orderId();
  
}
