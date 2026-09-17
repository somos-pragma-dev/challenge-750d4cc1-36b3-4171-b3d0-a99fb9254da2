package com.example.reconciliation.domain.repository;

import com.example.reconciliation.domain.model.Reconciliation;
import com.example.reconciliation.domain.model.Reconciliation.Status;
import java.time.Instant;
import java.util.Optional;
import java.util.UUID;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ReconciliationRepository {
    
    Mono<Reconciliation> save(Reconciliation reconciliation);
    
    Mono<Optional<Reconciliation>> findById(UUID id);
    
    Mono<Optional<Reconciliation>> findByEventIdAndVersion(String eventId, int version);
    
    Flux<Reconciliation> findByStatus(Status status);
    
    Flux<Reconciliation> findByStatusAndCreatedAtAfter(Status status, Instant since);
    
    Flux<Reconciliation> findBySourceTypeAndTargetType(String sourceType, String targetType);
    
    Flux<Reconciliation> findByCreatedAtBetween(Instant start, Instant end);
    
    Flux<Reconciliation> findPendingOlderThan(Instant cutoff);
    
    Mono<Long> countByStatus(Status status);
    
    Mono<Long> countByStatusAndCreatedAtAfter(Status status, Instant since);
    
    Mono<Boolean> existsByEventIdAndVersion(String eventId, int version);
    
    Mono<Void> deleteById(UUID id);
    
    Mono<Void> deleteOlderThan(Instant cutoff);
    
    Flux<Reconciliation> findAll(int page, int size);
    
    Mono<Long> count();
}