package dev.agasen.ecom.api.event.publisher;

import dev.agasen.ecom.api.event.DomainEvent;
import reactor.core.publisher.Flux;

public interface EventPublisher<T extends DomainEvent>{

  Flux<T> publish();
  
}
