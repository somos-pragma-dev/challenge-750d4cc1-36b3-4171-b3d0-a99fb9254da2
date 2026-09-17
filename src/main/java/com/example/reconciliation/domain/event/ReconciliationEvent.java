package com.example.reconciliation.domain.event;

import com.example.reconciliation.domain.model.Reconciliation.Status;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

public final class ReconciliationEvent {
    
    public enum EventType {
        CREATED,
        MATCHED,
        MISMATCHED,
        ESCALATED_TO_MANUAL,
        MANUALLY_RESOLVED,
        EXPIRED
    }
    
    private final UUID id;
    private final String eventId;
    private final int version;
    private final EventType eventType;
    private final UUID reconciliationId;
    private final String sourceType;
    private final String targetType;
    private final BigDecimal amount;
    private final String currency;
    private final String reference;
    private final Status previousStatus;
    private final Status newStatus;
    private final String reason;
    private final Instant timestamp;
    private final Instant processedAt;
    
    private ReconciliationEvent(Builder builder) {
        this.id = builder.id;
        this.eventId = builder.eventId;
        this.version = builder.version;
        this.eventType = builder.eventType;
        this.reconciliationId = builder.reconciliationId;
        this.sourceType = builder.sourceType;
        this.targetType = builder.targetType;
        this.amount = builder.amount;
        this.currency = builder.currency;
        this.reference = builder.reference;
        this.previousStatus = builder.previousStatus;
        this.newStatus = builder.newStatus;
        this.reason = builder.reason;
        this.timestamp = builder.timestamp;
        this.processedAt = builder.processedAt;
    }
    
    public static ReconciliationEvent created(UUID reconciliationId, String eventId, int version,
            String sourceType, String targetType, BigDecimal amount, String currency, String reference) {
        return builder()
            .id(UUID.randomUUID())
            .eventId(eventId)
            .version(version)
            .eventType(EventType.CREATED)
            .reconciliationId(reconciliationId)
            .sourceType(sourceType)
            .targetType(targetType)
            .amount(amount)
            .currency(currency)
            .reference(reference)
            .previousStatus(null)
            .newStatus(Status.PENDING)
            .timestamp(Instant.now())
            .processedAt(Instant.now())
            .build();
    }
    
    public static ReconciliationEvent matched(UUID reconciliationId, String eventId, int version,
            String sourceType, String targetType, BigDecimal amount, String reference) {
        return builder()
            .id(UUID.randomUUID())
            .eventId(eventId)
            .version(version)
            .eventType(EventType.MATCHED)
            .reconciliationId(reconciliationId)
            .sourceType(sourceType)
            .targetType(targetType)
            .amount(amount)
            .reference(reference)
            .previousStatus(Status.PENDING)
            .newStatus(Status.MATCHED)
            .timestamp(Instant.now())
            .processedAt(Instant.now())
            .build();
    }
    
    public static ReconciliationEvent mismatched(UUID reconciliationId, String eventId, int version,
            String sourceType, String targetType, BigDecimal amount, String reason) {
        return builder()
            .id(UUID.randomUUID())
            .eventId(eventId)
            .version(version)
            .eventType(EventType.MISMATCHED)
            .reconciliationId(reconciliationId)
            .sourceType(sourceType)
            .targetType(targetType)
            .amount(amount)
            .previousStatus(Status.PENDING)
            .newStatus(Status.MISMATCHED)
            .reason(reason)
            .timestamp(Instant.now())
            .processedAt(Instant.now())
            .build();
    }
    
    public static ReconciliationEvent escalated(UUID reconciliationId, String eventId, int version,
            Status previousStatus, String reason) {
        return builder()
            .id(UUID.randomUUID())
            .eventId(eventId)
            .version(version)
            .eventType(EventType.ESCALATED_TO_MANUAL)
            .reconciliationId(reconciliationId)
            .previousStatus(previousStatus)
            .newStatus(Status.MANUAL)
            .reason(reason)
            .timestamp(Instant.now())
            .processedAt(Instant.now())
            .build();
    }
    
    public static ReconciliationEvent manuallyResolved(UUID reconciliationId, String eventId, int version,
            Status previousStatus, String resolution) {
        return builder()
            .id(UUID.randomUUID())
            .eventId(eventId)
            .version(version)
            .eventType(EventType.MANUALLY_RESOLVED)
            .reconciliationId(reconciliationId)
            .previousStatus(previousStatus)
            .newStatus(Status.MATCHED)
            .reason(resolution)
            .timestamp(Instant.now())
            .processedAt(Instant.now())
            .build();
    }
    
    public static ReconciliationEvent expired(UUID reconciliationId, String eventId, int version) {
        return builder()
            .id(UUID.randomUUID())
            .eventId(eventId)
            .version(version)
            .eventType(EventType.EXPIRED)
            .reconciliationId(reconciliationId)
            .previousStatus(Status.PENDING)
            .newStatus(Status.PENDING)
            .reason("Ventana de reconciliacion expirada")
            .timestamp(Instant.now())
            .processedAt(Instant.now())
            .build();
    }
    
    public String getIdempotencyKey() {
        return eventId + ":" + version + ":" + eventType.name();
    }
    
    public UUID getId() {
        return id;
    }
    
    public String getEventId() {
        return eventId;
    }
    
    public int getVersion() {
        return version;
    }
    
    public EventType getEventType() {
        return eventType;
    }
    
    public UUID getReconciliationId() {
        return reconciliationId;
    }
    
    public String getSourceType() {
        return sourceType;
    }
    
    public String getTargetType() {
        return targetType;
    }
    
    public BigDecimal getAmount() {
        return amount;
    }
    
    public String getCurrency() {
        return currency;
    }
    
    public String getReference() {
        return reference;
    }
    
    public Status getPreviousStatus() {
        return previousStatus;
    }
    
    public Status getNewStatus() {
        return newStatus;
    }
    
    public String getReason() {
        return reason;
    }
    
    public Instant getTimestamp() {
        return timestamp;
    }
    
    public Instant getProcessedAt() {
        return processedAt;
    }
    
    public static Builder builder() {
        return new Builder();
    }
    
    public static final class Builder {
        private UUID id;
        private String eventId;
        private int version;
        private EventType eventType;
        private UUID reconciliationId;
        private String sourceType;
        private String targetType;
        private BigDecimal amount;
        private String currency;
        private String reference;
        private Status previousStatus;
        private Status newStatus;
        private String reason;
        private Instant timestamp;
        private Instant processedAt;
        
        private Builder() {}
        
        public Builder id(UUID id) {
            this.id = id;
            return this;
        }
        
        public Builder eventId(String eventId) {
            this.eventId = eventId;
            return this;
        }
        
        public Builder version(int version) {
            this.version = version;
            return this;
        }
        
        public Builder eventType(EventType eventType) {
            this.eventType = eventType;
            return this;
        }
        
        public Builder reconciliationId(UUID reconciliationId) {
            this.reconciliationId = reconciliationId;
            return this;
        }
        
        public Builder sourceType(String sourceType) {
            this.sourceType = sourceType;
            return this;
        }
        
        public Builder targetType(String targetType) {
            this.targetType = targetType;
            return this;
        }
        
        public Builder amount(BigDecimal amount) {
            this.amount = amount;
            return this;
        }
        
        public Builder currency(String currency) {
            this.currency = currency;
            return this;
        }
        
        public Builder reference(String reference) {
            this.reference = reference;
            return this;
        }
        
        public Builder previousStatus(Status previousStatus) {
            this.previousStatus = previousStatus;
            return this;
        }
        
        public Builder newStatus(Status newStatus) {
            this.newStatus = newStatus;
            return this;
        }
        
        public Builder reason(String reason) {
            this.reason = reason;
            return this;
        }
        
        public Builder timestamp(Instant timestamp) {
            this.timestamp = timestamp;
            return this;
        }
        
        public Builder processedAt(Instant processedAt) {
            this.processedAt = processedAt;
            return this;
        }
        
        public ReconciliationEvent build() {
            return new ReconciliationEvent(this);
        }
    }
}