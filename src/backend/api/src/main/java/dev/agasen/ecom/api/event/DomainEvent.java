package dev.agasen.ecom.api.event;

import java.time.Instant;

public interface DomainEvent {
  
  Instant createdAt();
  
}