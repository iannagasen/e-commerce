package dev.agasen.ecom.api.event.saga;

import reactor.core.publisher.Mono;

/**
 * T - type of the component
 */
public interface SagaComponentListener<T> {

  Mono<Void> onSuccess(T message);

  Mono<Void> onFailure(T message);

  Mono<Void> onRollback(T message);
  
}
