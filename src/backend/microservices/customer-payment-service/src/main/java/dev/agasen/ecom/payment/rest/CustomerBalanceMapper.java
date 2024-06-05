package dev.agasen.ecom.payment.rest;

import org.springframework.stereotype.Component;

import dev.agasen.ecom.api.core.payment.model.CustomerBalance;
import dev.agasen.ecom.api.rest.mapper.RestEntityMapper;
import dev.agasen.ecom.payment.persistence.CustomerBalanceEntity;

@Component
public class CustomerBalanceMapper implements RestEntityMapper<CustomerBalance, CustomerBalanceEntity>{

  @Override
  public CustomerBalance toRestModel(CustomerBalanceEntity m) {
    return CustomerBalance.builder()
      .balance(m.getBalance())
      .customerName(m.getCustomerName())
      .build();
  }

  @Override
  public CustomerBalanceEntity toEntityModel(CustomerBalance m) {
    return CustomerBalanceEntity.builder()
      .balance(m.balance())
      .customerName(m.customerName())
      .build();
  }
  
}
