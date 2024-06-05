package dev.agasen.ecom.payment;

import java.util.function.Function;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;

import dev.agasen.ecom.api.core.order.event.OrderEvent;
import dev.agasen.ecom.api.core.order.event.OrderEventProcessor;
import dev.agasen.ecom.api.core.payment.event.PaymentEvent;
import dev.agasen.ecom.api.event.util.MessageConverter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class OrderEventProcessorConfig {

  private final OrderEventProcessor<PaymentEvent> eventProcessor;

  @Bean
  public Function<Flux<Message<OrderEvent>>, Flux<Message<PaymentEvent>>> processor() {
    return flux -> flux
        .map(MessageConverter::toRecord)
        .doOnNext(r -> log.info("Customer payment receivedP{}", r.message()))
        .concatMap(r -> this.eventProcessor
            .process(r.message())
            // * use doOnSuccess rather than doOnNext since Event.Completted will emit an empty mono
            .doOnSuccess(e -> r.acknowledgement().acknowledge())
        )
        .map(this::toMessage);
  }

  private Message<PaymentEvent> toMessage(PaymentEvent event) {
    return MessageBuilder.withPayload(event)
          .setHeader(KafkaHeaders.KEY, event.orderId().toString())
          .build();
  }
  
}
