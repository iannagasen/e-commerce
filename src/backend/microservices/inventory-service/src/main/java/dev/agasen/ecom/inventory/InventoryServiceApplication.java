package dev.agasen.ecom.inventory;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

import dev.agasen.ecom.inventory.persistence.InventoryRepository;
import dev.agasen.ecom.inventory.persistence.InventoryUpdateRepository;

@SpringBootApplication
@ComponentScan("dev.agasen")
public class InventoryServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(InventoryServiceApplication.class, args);
	}

	@Bean
	public CommandLineRunner run(InventoryRepository inventoryRepo, InventoryUpdateRepository inventoryHistoRepo) {
		return args -> {
			// inventoryRepo.save(new InventoryEntity("1", 1L, 10)).block();
			// inventoryRepo.save(new InventoryEntity("1", 2L, 10)).block();
		};
	}

}
