package dev.agasen.ecom.payment;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MongoDBContainer;

@DirtiesContext
@SpringBootTest(properties={
  "logging.level.root=ERROR",
  "logging.level.dev.agasen*=INFO",
  "spring.cloud.stream.kafka.binder.configuration.auto.offset.reset=earliest",
  "spring.cloud.stream.kafka.binder.brokers=localhost:9092"
})
@EmbeddedKafka(
  partitions = 1,
  bootstrapServersProperty = "spring.kafka.bootstrap-servers"
)
public abstract class BaseIntegrationTest {
  /**
   * BaseIntegration test that will configure Kafka and MongoDB
   */

  private static final String MONGODB_VER = "mongo:6.0.4";
  protected static final MongoDBContainer MONGODB = new MongoDBContainer(MONGODB_VER);

  static {
    MONGODB.start();
  }

  @DynamicPropertySource
  static void setProperties(DynamicPropertyRegistry registry) {
    registry.add("spring.data.mongodb.host", MONGODB::getContainerIpAddress);
    registry.add("spring.data.mongodb.port", () -> MONGODB.getMappedPort(27017));
    registry.add("spring.data.mongodb.database", () -> "test");
  }
  
}
