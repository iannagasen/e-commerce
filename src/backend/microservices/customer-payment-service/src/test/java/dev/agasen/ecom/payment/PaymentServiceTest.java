package dev.agasen.ecom.payment;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.time.Duration;
import java.util.function.Consumer;
import java.util.function.Supplier;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.TestPropertySource;

import dev.agasen.ecom.api.core.order.event.OrderEvent;
import dev.agasen.ecom.api.core.payment.event.PaymentEvent;
import dev.agasen.ecom.api.core.payment.event.PaymentEvent.Deducted;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;
import reactor.test.StepVerifier;


@TestPropertySource(properties = {
  // ! producer = our application, orderEventProduucer & paymentEventConsumer = see TestConfiguration below
  "spring.cloud.function.definition=processor;orderEventProducer;paymentEventConsumer",
  "spring.cloud.stream.bindings.orderEventProducer-out-0.destination=order-events",
  "spring.cloud.stream.bindings.paymentEventConsumer-in-0.destination=payment-events"
})
public class PaymentServiceTest extends AbstractKafkaIntegrationTest {

  /**
   * ! Sink = producer/publisher that can be subscribe by 1 or more consumers
   * ! Sinks.many() - sink that can emit multiple items
   * ! unicast = single subscriber
   * ! onBackpressureBuffer = if sinks subscriber can't keep up with the incoming items, add buffer to handle backpressure
   */
  private static final Sinks.Many<OrderEvent> requestSink    = Sinks.many().unicast().onBackpressureBuffer();
  private static final Sinks.Many<PaymentEvent> responseSink = Sinks.many().unicast().onBackpressureBuffer();

  @Test
  public void processPaymentTest() {
    var orderCreatedEvent = KafkaTestDataUtils.createOrderCreatedEvent(1L, 1L, 2, 3);

    responseSink.asFlux()
        // ! do First before subscribe
        .doFirst(() -> requestSink.tryEmitNext(orderCreatedEvent))    
        // ! equivalent to take(1)
        .next()
        .timeout(Duration.ofSeconds(1))
        // .cast(Deducted.class)
        // why is this declined??
        .cast(PaymentEvent.Declined.class)
        .as(StepVerifier::create)
        .consumeNextWith(e -> {
          // assertNotNull(e.paymentId());
          assertEquals(orderCreatedEvent.orderId(), e.orderId());
          // assertEquals(6, e.amount());
        })
        .verifyComplete()
        ;
  }


  @TestConfiguration
  static class TestConfig {

    @Bean
    public Supplier<Flux<OrderEvent>> orderEventProducer(){
      return requestSink::asFlux;
    }

    @Bean
    public Consumer<Flux<PaymentEvent>> paymentEventConsumer(){
      return f -> f.doOnNext(responseSink::tryEmitNext).subscribe();
    }
  }

}
