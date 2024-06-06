package dev.agasen.ecom.payment;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.time.Duration;
import java.util.function.Consumer;
import java.util.function.Supplier;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.TestPropertySource;

import dev.agasen.ecom.api.core.order.event.OrderEvent;
import dev.agasen.ecom.api.core.payment.event.PaymentEvent;
import dev.agasen.ecom.payment.persistence.CustomerBalanceEntity;
import dev.agasen.ecom.payment.persistence.CustomerBalanceRepository;
import dev.agasen.ecom.payment.persistence.CustomerPaymentEntity;
import dev.agasen.ecom.payment.persistence.CustomerPaymentRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;
import reactor.test.StepVerifier;


@TestPropertySource(properties = {
  // ! producer = our application, orderEventProduucer & paymentEventConsumer = see TestConfiguration below
  "spring.cloud.function.definition=processor;orderEventProducer;paymentEventConsumer",
  "spring.cloud.stream.bindings.orderEventProducer-out-0.destination=order-events",
  "spring.cloud.stream.bindings.paymentEventConsumer-in-0.destination=payment-events"
})
public class PaymentServiceTest extends BaseIntegrationTest {

  /**
   * ! Sink = producer/publisher that can be subscribe by 1 or more consumers
   * ! Sinks.many() - sink that can emit multiple items
   * ! unicast = single subscriber
   * ! onBackpressureBuffer = if sinks subscriber can't keep up with the incoming items, add buffer to handle backpressure
   */
  private static final Sinks.Many<OrderEvent> requestSink    = Sinks.many().unicast().onBackpressureBuffer();
  private static final Sinks.Many<PaymentEvent> responseSink = Sinks.many().unicast().onBackpressureBuffer();

  @Autowired private CustomerBalanceRepository balanceRepository;
  @Autowired private CustomerPaymentRepository paymentRepository;

  @BeforeEach
  public void setup() {
    balanceRepository.deleteAll().block();
    paymentRepository.deleteAll().block();

    balanceRepository.save(new CustomerBalanceEntity(null, 1L, "Ian", 100L)).block();
    balanceRepository.save(new CustomerBalanceEntity(null, 2L, "Neil", 100L)).block();
    balanceRepository.save(new CustomerBalanceEntity(null, 3L, "Sabel", 100L)).block();

    // paymentRepository.save(new CustomerPaymentEntity(null, 1L, 1L, 1L, null, 0))
  }

  @Test
  public void processPaymentTest() {
    var orderCreatedEvent = KafkaTestDataUtils.createOrderCreatedEvent(1L, 1L, 2, 3);

    /**
     * ! listen to PaymentEvent (w/c is responseSink)
     */
    responseSink.asFlux()
        // ! do First before subscribe
        .doFirst(() -> requestSink.tryEmitNext(orderCreatedEvent))    
        // ! equivalent to take(1)
        .next()
        .timeout(Duration.ofSeconds(100))
        .cast(PaymentEvent.Deducted.class)
        // why is this declined??
        // .cast(PaymentEvent.Declined.class)
        .as(StepVerifier::create)
        .consumeNextWith(e -> {
          assertNotNull(e.paymentId());
          assertEquals(orderCreatedEvent.orderId(), e.orderId());
          assertEquals(6, e.amount());
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
