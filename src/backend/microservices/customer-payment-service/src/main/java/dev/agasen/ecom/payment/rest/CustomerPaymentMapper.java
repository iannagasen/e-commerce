package dev.agasen.ecom.payment.rest;

import org.springframework.stereotype.Component;

import dev.agasen.ecom.api.core.payment.model.CustomerPayment;
import dev.agasen.ecom.api.core.payment.model.PaymentProcessRequest;
import dev.agasen.ecom.api.rest.mapper.RestEntityMapper;
import dev.agasen.ecom.payment.persistence.CustomerPaymentEntity;

@Component
public class CustomerPaymentMapper implements RestEntityMapper<CustomerPayment, CustomerPaymentEntity> {

  @Override
  public CustomerPayment toRestModel(CustomerPaymentEntity e) {
    return CustomerPayment.builder()
      .id(e.getId())
      .status(e.getStatus())
      .customerId(e.getCustomerId())
      .orderId(e.getOrderId())
      .amount(e.getAmount())
      .build();
  }

  @Override
  public CustomerPaymentEntity toEntityModel(CustomerPayment r) {
    return CustomerPaymentEntity.builder()
      .id(r.id())
      .status(r.status())
      .customerId(r.customerId())
      .orderId(r.orderId())
      .amount(r.amount())
      .build();
  }

  public CustomerPaymentEntity toEntityModel(PaymentProcessRequest r) {
    return CustomerPaymentEntity.builder()
      .customerId(r.customerId())
      .orderId(r.orderId())
      .amount(r.amount())
      .build();
  }
  
}
