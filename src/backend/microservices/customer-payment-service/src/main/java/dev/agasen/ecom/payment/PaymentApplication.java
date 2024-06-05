package dev.agasen.ecom.payment;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.data.mapping.context.MappingContext;
import org.springframework.data.mongodb.core.ReactiveMongoOperations;
import org.springframework.data.mongodb.core.index.IndexResolver;
import org.springframework.data.mongodb.core.index.MongoPersistentEntityIndexResolver;
import org.springframework.data.mongodb.core.index.ReactiveIndexOperations;
import org.springframework.data.mongodb.core.mapping.MongoPersistentEntity;
import org.springframework.data.mongodb.core.mapping.MongoPersistentProperty;
import org.springframework.web.bind.annotation.RestController;

import dev.agasen.ecom.payment.persistence.CustomerBalanceEntity;
import dev.agasen.ecom.payment.persistence.CustomerBalanceRepository;
import dev.agasen.ecom.payment.persistence.CustomerPaymentEntity;
import dev.agasen.ecom.payment.persistence.CustomerPaymentRepository;
import org.springframework.web.bind.annotation.GetMapping;


@SpringBootApplication
@ComponentScan("dev.agasen")
@RestController
public class PaymentApplication {

	public static void main(String[] args) {
		SpringApplication.run(PaymentApplication.class, args);
	}

	@GetMapping("/test")
	public String test() {
		return "IAAAAAN";
	}
	

	// @Bean
	public CommandLineRunner runner(CustomerPaymentRepository paymentRepo,
																	CustomerBalanceRepository balanceRepo) {
		return args -> {
			// balanceRepo.saveAll(List.of(
			// 	CustomerBalanceEntity.builder()
			// 		.customerName("Ian")
			// 		.balance(100L)
			// 		.build(),
			// 	CustomerBalanceEntity.builder()
			// 		.customerName("Neil")
			// 		.balance(200L)
			// 		.build(),
			// 	CustomerBalanceEntity.builder()
			// 		.customerName("Sabel")
			// 		.balance(300L)
			// 		.build()
			// )).doOnNext(c -> System.out.println("SAVED!! --- " + c.getCustomerName()))
			// 	.doOnComplete(() -> System.out.println("SAVED ALL"))
			// 	.then().block();

			// balanceRepo.deleteAll().block();

			balanceRepo.save(
				CustomerBalanceEntity.builder()
					.customerId(1L)
					.customerName("Ian")
					.balance(100L)
					.build()
			).block();
			balanceRepo.save(
				CustomerBalanceEntity.builder()
					.customerId(2L)
					.customerName("Neil")
					.balance(100L)
					.build()
			).block();
			
			balanceRepo.save(
				CustomerBalanceEntity.builder()
					.customerId(3L)
					.customerName("Sabel")
					.balance(100L)
					.build()
			).block();
		};
	}

	@Autowired
  ReactiveMongoOperations mongoTemplate;

  @EventListener(ContextRefreshedEvent.class)
  public void initIndicesAfterStartup() {

    MappingContext<? extends MongoPersistentEntity<?>, MongoPersistentProperty> mappingContext = mongoTemplate.getConverter().getMappingContext();
    IndexResolver resolver = new MongoPersistentEntityIndexResolver(mappingContext);

    ReactiveIndexOperations indexOps = mongoTemplate.indexOps(CustomerBalanceEntity.class);
    resolver.resolveIndexFor(CustomerBalanceEntity.class).forEach(e -> indexOps.ensureIndex(e).block());

    ReactiveIndexOperations indexOps2 = mongoTemplate.indexOps(CustomerPaymentEntity.class);
    resolver.resolveIndexFor(CustomerPaymentEntity.class).forEach(e -> indexOps2.ensureIndex(e).block());
  }

}
