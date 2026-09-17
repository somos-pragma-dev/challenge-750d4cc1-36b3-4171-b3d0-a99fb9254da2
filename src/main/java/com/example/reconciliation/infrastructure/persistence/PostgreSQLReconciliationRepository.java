package com.example.reconciliation.infrastructure.persistence;

import com.example.reconciliation.domain.model.Reconciliation;
import com.example.reconciliation.domain.model.Reconciliation.Status;
import com.example.reconciliation.domain.repository.ReconciliationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.data.r2dbc.repository.Modifying;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.data.relational.core.mapping.Table;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Repository
@Table("reconciliations")
public interface PostgreSQLReconciliationRepository extends R2dbcRepository<ReconciliationEntity, UUID>, 
                                                            ReconciliationRepository {
    
    @Query("SELECT * FROM reconciliations WHERE event_id = :eventId AND version = :version")
    Mono<ReconciliationEntity> findByEventIdAndVersion(String eventId, int version);
    
    @Query("SELECT * FROM reconciliations WHERE status = :status")
    Flux<ReconciliationEntity> findByStatus(Status status);
    
    @Query("SELECT * FROM reconciliations WHERE status = :status AND created_at > :since")
    Flux<ReconciliationEntity> findByStatusAndCreatedAtAfter(Status status, Instant since);
    
    @Query("SELECT * FROM reconciliations WHERE source_type = :sourceType AND target_type = :targetType")
    Flux<ReconciliationEntity> findBySourceTypeAndTargetType(String sourceType, String targetType);
    
    @Query("SELECT * FROM reconciliations WHERE created_at BETWEEN :start AND :end")
    Flux<ReconciliationEntity> findByCreatedAtBetween(Instant start, Instant end);
    
    @Query("SELECT * FROM reconciliations WHERE status = 'PENDING' AND created_at < :cutoff")
    Flux<ReconciliationEntity> findPendingOlderThan(Instant cutoff);
    
    @Query("SELECT COUNT(*) FROM reconciliations WHERE status = :status")
    Mono<Long> countByStatus(Status status);
    
    @Query("SELECT COUNT(*) FROM reconciliations WHERE status = :status AND created_at > :since")
    Mono<Long> countByStatusAndCreatedAtAfter(Status status, Instant since);
    
    @Query("SELECT EXISTS(SELECT 1 FROM reconciliations WHERE event_id = :eventId AND version = :version)")
    Mono<Boolean> existsByEventIdAndVersion(String eventId, int version);
    
    @Modifying
    @Query("DELETE FROM reconciliations WHERE id = :id")
    Mono<Void> deleteById(UUID id);
    
    @Modifying
    @Query("DELETE FROM reconciliations WHERE created_at < :cutoff")
    Mono<Void> deleteOlderThan(Instant cutoff);
    
    @Override
    default Mono<Reconciliation> save(Reconciliation reconciliation) {
        return findById(reconciliation.getId())
            .flatMap(existing -> {
                ReconciliationEntity updated = toEntity(reconciliation);
                updated.setVersion(existing.getVersion() + 1);
                updated.setUpdatedAt(Instant.now());
                return save(updated);
            })
            .switchIfEmpty(Mono.defer(() -> {
                ReconciliationEntity entity = toEntity(reconciliation);
                entity.setVersion(1);
                entity.setCreatedAt(Instant.now());
                entity.setUpdatedAt(Instant.now());
                return save(entity);
            }))
            .map(this::toDomain);
    }
    
    @Override
    default Mono<Optional<Reconciliation>> findById(UUID id) {
        return findById(id)
            .map(entity -> Optional.ofNullable(entity).map(this::toDomain))
            .defaultIfEmpty(Optional.empty());
    }
    
    @Override
    default Mono<Optional<Reconciliation>> findByEventIdAndVersion(String eventId, int version) {
        return findByEventIdAndVersion(eventId, version)
            .map(entity -> Optional.ofNullable(entity).map(this::toDomain))
            .defaultIfEmpty(Optional.empty());
    }
    
    @Override
    default Flux<Reconciliation> findByStatus(Status status) {
        return findByStatus(status).map(this::toDomain);
    }
    
    @Override
    default Flux<Reconciliation> findByStatusAndCreatedAtAfter(Status status, Instant since) {
        return findByStatusAndCreatedAtAfter(status, since).map(this::toDomain);
    }
    
    @Override
    default Flux<Reconciliation> findBySourceTypeAndTargetType(String sourceType, String targetType) {
        return findBySourceTypeAndTargetType(sourceType, targetType).map(this::toDomain);
    }
    
    @Override
    default Flux<Reconciliation> findByCreatedAtBetween(Instant start, Instant end) {
        return findByCreatedAtBetween(start, end).map(this::toDomain);
    }
    
    @Override
    default Flux<Reconciliation> findPendingOlderThan(Instant cutoff) {
        return findPendingOlderThan(cutoff).map(this::toDomain);
    }
    
    @Override
    default Flux<Reconciliation> findAll(int page, int size) {
        return findAll(Pageable.ofSize(size).withPage(page)).map(this::toDomain);
    }
    
    private ReconciliationEntity toEntity(Reconciliation reconciliation) {
        ReconciliationEntity entity = new ReconciliationEntity();
        entity.setId(reconciliation.getId());
        entity.setEventId(reconciliation.getEventId());
        entity.setVersion(reconciliation.getVersion());
        entity.setSourceType(reconciliation.getSourceType());
        entity.setTargetType(reconciliation.getTargetType());
        entity.setStatus(reconciliation.getStatus());
        entity.setAmount(reconciliation.getAmount());
        entity.setCurrency(reconciliation.getCurrency());
        entity.setReference(reconciliation.getReference());
        entity.setDescription(reconciliation.getDescription());
        entity.setMatchedAt(reconciliation.getMatchedAt());
        entity.setCreatedAt(reconciliation.getCreatedAt());
        entity.setUpdatedAt(reconciliation.getUpdatedAt());
        entity.setMismatchReason(reconciliation.getMismatchReason());
        entity.setManualResolution(reconciliation.getManualResolution());
        return entity;
    }
    
    private Reconciliation toDomain(ReconciliationEntity entity) {
        return Reconciliation.restore(
            entity.getId(),
            entity.getEventId(),
            entity.getVersion(),
            entity.getSourceType(),
            entity.getTargetType(),
            entity.getStatus(),
            entity.getAmount(),
            entity.getCurrency(),
            entity.getReference(),
            entity.getDescription(),
            entity.getMatchedAt(),
            entity.getCreatedAt(),
            entity.getUpdatedAt(),
            entity.getMismatchReason(),
            entity.getManualResolution()
        );
    }
    
    @Table("reconciliations")
    public static class ReconciliationEntity {
        private UUID id;
        private String eventId;
        private int version;
        private String sourceType;
        private String targetType;
        private Status status;
        private BigDecimal amount;
        private String currency;
        private String reference;
        private String description;
        private Instant matchedAt;
        private Instant createdAt;
        private Instant updatedAt;
        private String mismatchReason;
        private String manualResolution;
        
        public UUID getId() {
            return id;
        }
        
        public void setId(UUID id) {
            this.id = id;
        }
        
        public String getEventId() {
            return eventId;
        }
        
        public void setEventId(String eventId) {
            this.eventId = eventId;
        }
        
        public int getVersion() {
            return version;
        }
        
        public void setVersion(int version) {
            this.version = version;
        }
        
        public String getSourceType() {
            return sourceType;
        }
        
        public void setSourceType(String sourceType) {
            this.sourceType = sourceType;
        }
        
        public String getTargetType() {
            return targetType;
        }
        
        public void setTargetType(String targetType) {
            this.targetType = targetType;
        }
        
        public Status getStatus() {
            return status;
        }
        
        public void setStatus(Status status) {
            this.status = status;
        }
        
        public BigDecimal getAmount() {
            return amount;
        }
        
        public void setAmount(BigDecimal amount) {
            this.amount = amount;
        }
        
        public String getCurrency() {
            return currency;
        }
        
        public void setCurrency(String currency) {
            this.currency = currency;
        }
        
        public String getReference() {
            return reference;
        }
        
        public void setReference(String reference) {
            this.reference = reference;
        }
        
        public String getDescription() {
            return description;
        }
        
        public void setDescription(String description) {
            this.description = description;
        }
        
        public Instant getMatchedAt() {
            return matchedAt;
        }
        
        public void setMatchedAt(Instant matchedAt) {
            this.matchedAt = matchedAt;
        }
        
        public Instant getCreatedAt() {
            return createdAt;
        }
        
        public void setCreatedAt(Instant createdAt) {
            this.createdAt = createdAt;
        }
        
        public Instant getUpdatedAt() {
            return updatedAt;
        }
        
        public void setUpdatedAt(Instant updatedAt) {
            this.updatedAt = updatedAt;
        }
        
        public String getMismatchReason() {
            return mismatchReason;
        }
        
        public void setMismatchReason(String mismatchReason) {
            this.mismatchReason = mismatchReason;
        }
        
        public String getManualResolution() {
            return manualResolution;
        }
        
        public void setManualResolution(String manualResolution) {
            this.manualResolution = manualResolution;
        }
    }
}