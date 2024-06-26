package dev.agasen.ecom.order.messaging.participants;

import org.springframework.stereotype.Service;

import dev.agasen.ecom.api.saga.order.event.OrderComponentFetcher;
import dev.agasen.ecom.api.saga.order.event.OrderComponentListener;
import dev.agasen.ecom.api.saga.order.model.OrderComponent.OrderPayment;
import dev.agasen.ecom.order.persistence.OrderComponentEntity;
import dev.agasen.ecom.order.persistence.OrderComponentRepository;
import dev.agasen.ecom.order.util.OrderMapper;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class PaymentComponentService implements OrderComponentFetcher.Payment, OrderComponentListener.Payment {

  private final OrderComponentRepository repo;

  @Override
  public Mono<OrderPayment> getComponent(Long orderId) {
    return this.repo.findOrderPaymentByOrderId(orderId).map(OrderMapper::toOrderPayment);
  }

  @Override
  public Mono<Void> onSuccess(OrderPayment message) {
    return this.repo.findOrderPaymentByOrderId(message.orderId())
        .switchIfEmpty(Mono.defer(() -> {
            var entity = OrderMapper.toOrderPaymentEntity(message);
            entity.setSuccessful(true);
            return this.repo.save(entity);
        }))
        .then();
  }

  @Override
  public Mono<Void> onFailure(OrderPayment message) {
    return this.repo.findOrderPaymentByOrderId(message.orderId())
        .switchIfEmpty(Mono.defer(() -> {
            var entity = OrderMapper.toOrderPaymentEntity(message);
            entity.setSuccessful(false);
            return this.repo.save(entity);
        }))
        .then();
  }

  @Override
  public Mono<Void> onRollback(OrderPayment message) {
    return this.repo.findOrderPaymentByOrderId(message.orderId())
        .doOnNext(e -> e.setStatus(message.status()))
        .flatMap(this.repo::save)
        .then();
  }

}
