package dev.agasen.ecom.inventory;

import java.util.function.Function;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;

import dev.agasen.ecom.api.core.inventory.event.InventoryEvent;
import dev.agasen.ecom.api.core.order.event.OrderEvent;
import dev.agasen.ecom.api.core.order.event.OrderEventProcessor;
import dev.agasen.ecom.api.event.util.MessageConverter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;

@Configuration
@Slf4j
@RequiredArgsConstructor
public class OrderEventHandler {

  private final OrderEventProcessor<InventoryEvent> orderEventProcessor;

  @Bean
  public Function<Flux<Message<OrderEvent>>, Flux<Message<InventoryEvent>>> processor() {
    return orderEventFlux -> orderEventFlux
        .map(MessageConverter::toRecord)
        .doOnNext(orderEventRecord -> log.info("Customer inventory update received: {}", orderEventRecord.message()))
        .concatMap(orderEventRecord -> this.orderEventProcessor
            .process(orderEventRecord.message())
            .doOnSuccess(event -> orderEventRecord.acknowledgement().acknowledge()))
        .map(paymentEvent -> MessageBuilder.withPayload(paymentEvent)
            .setHeader(KafkaHeaders.KEY, paymentEvent.orderId())
            .build());
  }
  
}
