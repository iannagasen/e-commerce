package dev.agasen.ecom.api.event.saga;

import reactor.core.publisher.Mono;

/**
 * T - Component
 * D - saga Id type
 */
public interface SagaComponentFetcher<T, D> {
  Mono<T> getComponent(D sagaId);  
}
