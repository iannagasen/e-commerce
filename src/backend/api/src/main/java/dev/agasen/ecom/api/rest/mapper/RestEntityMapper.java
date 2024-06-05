package dev.agasen.ecom.api.rest.mapper;

public interface RestEntityMapper<REST, ENTITY> {
  
  REST toRestModel(ENTITY m);
  ENTITY toEntityModel(REST m);

}
