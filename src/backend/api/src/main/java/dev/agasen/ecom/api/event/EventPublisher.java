package dev.agasen.ecom.api.event;

import reactor.core.publisher.Flux;

public interface EventPublisher<T extends DomainEvent>{

  Flux<T> publish();
  
}
