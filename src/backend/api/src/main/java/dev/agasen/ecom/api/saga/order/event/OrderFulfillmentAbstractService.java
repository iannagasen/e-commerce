package dev.agasen.ecom.api.saga.order.event;

import dev.agasen.ecom.api.saga.order.model.PurchaseOrder;
import reactor.core.publisher.Mono;

public interface OrderFulfillmentAbstractService {
  
  /**
   * Complete the order. <br>
   *  Steps: <br>
   *  1. Fetch all the components of the order. <br>
   *  2. Check if all the components are successful. <br>
   *  3. If all the components are successful, then complete the order. <br> 
   * @param orderId
   * @return
   */
  Mono<PurchaseOrder> complete(Long orderId);

  Mono<PurchaseOrder> cancel(Long orderId);
}
