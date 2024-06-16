package dev.agasen.ecom.api.event.saga;

import reactor.core.publisher.Mono;

/**
 * T - type of the component
 */
public interface SagaComponentListener<T> {

  /**
   * Standard implementation: <br/>
   *  1. Locate the entity by its ID. <br/>
   *  2. Ensure the entity is null to prevent duplicate processing. <br/>
   *  3. If the entity is not null, convert the provided message to an entity and mark it as successful.
   */
  Mono<Void> onSuccess(T message);

  /**
   * Standard implementation: <br/>
   *  1. Locate the entity by its ID. <br/>
   *  2. Ensure the entity is null to prevent duplicate processing. <br/>
   *  3. If the entity is not null, convert the provided message to an entity and mark it as unsuccessful.
   */
  Mono<Void> onFailure(T message);

  /**
   * normal implementation: <br/>
   *  1. find the entity by id; <br/>
   *  2. entity should be null - to avoid duplicate processing <br/>
   *  3. if not null, transform the message to entity and set the status of the message to why it is needed to rollback
   */
  Mono<Void> onRollback(T message);
  
}
