package dev.agasen.ecom.order.util;

import org.springframework.data.domain.Sort.Order;

import dev.agasen.ecom.api.saga.order.event.OrderStatus;
import dev.agasen.ecom.api.saga.order.model.OrderComponent;
import dev.agasen.ecom.api.saga.order.model.OrderComponent.OrderInventory;
import dev.agasen.ecom.api.saga.order.model.OrderCreateRequest;
import dev.agasen.ecom.api.saga.order.model.OrderDetails;
import dev.agasen.ecom.api.saga.order.model.PurchaseOrder;
import dev.agasen.ecom.order.persistence.OrderComponentEntity;
import dev.agasen.ecom.order.persistence.PurchaseOrderEntity;

public record OrderMapper() {

  /**
   * create request to entity
   */
  public static PurchaseOrderEntity toPurchaseOrderEntity(OrderCreateRequest req) {
    return PurchaseOrderEntity.builder()
        .status(OrderStatus.PENDING)
        .customerId(req.customerId())
        .productId(req.productId())
        .quantity(req.quantity())
        .unitPrice(req.unitPrice())
        .amount(req.quantity() *  req.unitPrice())
        .build();
  }

  /**
   * entity to dto model
   */
  public static PurchaseOrder toPurchaseOrder(PurchaseOrderEntity e) {
    return PurchaseOrder.builder()
        .orderId(e.getOrderId())
        .unitPrice(e.getUnitPrice())
        .quantity(e.getQuantity())
        .productId(e.getProductId())
        .amount(e.getAmount())
        .customerId(e.getCustomerId())
        .orderStatus(e.getStatus())
        .deliveryDate(e.getDeliveryDate())
        .build();
  }

  public static OrderComponentEntity.Payment toOrderPaymentEntity(OrderComponent.OrderPayment op) {
    return OrderComponentEntity.Payment.builder()
        .orderId(op.orderId())
        .paymentId(op.paymentId())
        .status(op.status())
        .message(op.message())
        .build();
  }

  public static OrderComponentEntity.Payment toOrderPaymentEntity(OrderComponent.OrderPayment op, boolean successful) {
    return OrderComponentEntity.Payment.builder()
        .orderId(op.orderId())
        .paymentId(op.paymentId())
        .status(op.status())
        .message(op.message())
        .successful(successful)
        .build();
  }

  public static OrderComponent.OrderPayment toOrderPayment(OrderComponentEntity.Payment op) {
    return OrderComponent.OrderPayment.builder()
        .orderId(op.getOrderId())
        .paymentId(op.getPaymentId())
        .status(op.getStatus())
        .message(op.getMessage())
        .build();
  }

  public static OrderComponentEntity.Inventory toOrderInventory(OrderComponent.OrderInventory oi) {
    return OrderComponentEntity.Inventory.builder()
        .inventoryId(oi.inventoryId())
        .orderId(oi.orderId())
        .status(oi.status())
        .message(oi.message())
        .build();
  }

  public static OrderComponent.OrderInventory toOrderInventory(OrderComponentEntity.Inventory oi) {
    return OrderComponent.OrderInventory.builder()
        .inventoryId(oi.getInventoryId())
        .orderId(oi.getOrderId())
        .status(oi.getStatus())
        .message(oi.getMessage())
        .build();
  }

  public static OrderDetails toOrderDetails(PurchaseOrder o, OrderComponent.OrderPayment p, OrderComponent.OrderInventory i) {
    return OrderDetails.builder()
        .inventory(i)
        .order(o)
        .payment(p)
        .build();
  }

  public static OrderComponentEntity.Inventory toOrderInventoryEntity(OrderInventory message) {
    return OrderComponentEntity.Inventory.builder()
        .orderId(message.orderId())
        .inventoryId(message.inventoryId())
        .status(message.status())
        .message(message.message())
        .build();
  }

  public static OrderComponentEntity.Shipping toOrderShippingEntity(OrderComponent.OrderShipment message) {
    return OrderComponentEntity.Shipping.builder()
        .orderId(message.orderId())
        .shippingId(message.shippingId())
        .status(message.status())
        .message(message.message())
        .build();
  }

  public static OrderComponent.OrderShipment toOrderShipment(OrderComponentEntity.Shipping oi) {
    return OrderComponent.OrderShipment.builder()
        .orderId(oi.getOrderId())
        .shippingId(oi.getShippingId())
        .status(oi.getStatus())
        .message(oi.getMessage())
        .build();
  }

}
