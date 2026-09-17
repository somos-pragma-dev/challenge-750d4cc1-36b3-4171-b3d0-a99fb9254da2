package com.example.reconciliation.domain.model;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

public final class Reconciliation {
    
    public enum Status {
        PENDING("Pendiente de match"),
        MATCHED("Coincidencia encontrada"),
        MISMATCHED("Discrepancia detectada"),
        MANUAL("Requiere revisión manual");
        
        private final String description;
        
        Status(String description) {
            this.description = description;
        }
        
        public String getDescription() {
            return description;
        }
    }
    
    private final UUID id;
    private final String eventId;
    private final int version;
    private final String sourceType;
    private final String targetType;
    private Status status;
    private final BigDecimal amount;
    private final String currency;
    private final String reference;
    private final String description;
    private Instant matchedAt;
    private final Instant createdAt;
    private Instant updatedAt;
    private String mismatchReason;
    private String manualResolution;
    
    private Reconciliation(Builder builder) {
        this.id = builder.id;
        this.eventId = builder.eventId;
        this.version = builder.version;
        this.sourceType = builder.sourceType;
        this.targetType = builder.targetType;
        this.status = builder.status;
        this.amount = builder.amount;
        this.currency = builder.currency;
        this.reference = builder.reference;
        this.description = builder.description;
        this.matchedAt = builder.matchedAt;
        this.createdAt = builder.createdAt;
        this.updatedAt = builder.updatedAt;
        this.mismatchReason = builder.mismatchReason;
        this.manualResolution = builder.manualResolution;
    }
    
    public static Reconciliation create(String eventId, int version, String sourceType, 
            String targetType, BigDecimal amount, String currency, String reference, String description) {
        Objects.requireNonNull(eventId, "El eventId no puede ser null");
        Objects.requireNonNull(sourceType, "El sourceType no puede ser null");
        Objects.requireNonNull(targetType, "El targetType no puede ser null");
        Objects.requireNonNull(amount, "El amount no puede ser null");
        Objects.requireNonNull(currency, "El currency no puede ser null");
        
        if (version < 0) {
            throw new IllegalArgumentException("La versión no puede ser negativa");
        }
        
        Instant now = Instant.now();
        return Builder.create()
                .id(UUID.randomUUID())
                .eventId(eventId)
                .version(version)
                .sourceType(sourceType)
                .targetType(targetType)
                .status(Status.PENDING)
                .amount(amount)
                .currency(currency)
                .reference(reference)
                .description(description)
                .createdAt(now)
                .updatedAt(now)
                .build();
    }
    
    public static Reconciliation restore(UUID id, String eventId, int version, String sourceType,
            String targetType, Status status, BigDecimal amount, String currency, String reference,
            String description, Instant matchedAt, Instant createdAt, Instant updatedAt,
            String mismatchReason, String manualResolution) {
        return Builder.create()
                .id(id)
                .eventId(eventId)
                .version(version)
                .sourceType(sourceType)
                .targetType(targetType)
                .status(status)
                .amount(amount)
                .currency(currency)
                .reference(reference)
                .description(description)
                .matchedAt(matchedAt)
                .createdAt(createdAt)
                .updatedAt(updatedAt)
                .mismatchReason(mismatchReason)
                .manualResolution(manualResolution)
                .build();
    }
    
    public boolean canTransitionTo(Status newStatus) {
        return switch (status) {
            case PENDING -> newStatus == Status.MATCHED || 
                             newStatus == Status.MISMATCHED || 
                             newStatus == Status.MANUAL;
            case MISMATCHED -> newStatus == Status.MANUAL || newStatus == Status.MATCHED;
            case MANUAL -> newStatus == Status.MATCHED;
            case MATCHED -> false;
        };
    }
    
    public void match() {
        if (!canTransitionTo(Status.MATCHED)) {
            throw new IllegalStateException(
                String.format("No se puede transicionar de %s a MATCHED", status)
            );
        }
        this.status = Status.MATCHED;
        this.matchedAt = Instant.now();
        this.updatedAt = Instant.now();
    }
    
    public void mismatch(String reason) {
        if (!canTransitionTo(Status.MISMATCHED)) {
            throw new IllegalStateException(
                String.format("No se puede transicionar de %s a MISMATCHED", status)
            );
        }
        Objects.requireNonNull(reason, "La razón del desbalance no puede ser null");
        this.status = Status.MISMATCHED;
        this.mismatchReason = reason;
        this.updatedAt = Instant.now();
    }
    
    public void escalateToManual(String reason) {
        if (!canTransitionTo(Status.MANUAL)) {
            throw new IllegalStateException(
                String.format("No se puede transicionar de %s a MANUAL", status)
            );
        }
        this.status = Status.MANUAL;
        this.mismatchReason = reason;
        this.updatedAt = Instant.now();
    }
    
    public void resolveManually(String resolution) {
        if (status != Status.MANUAL) {
            throw new IllegalStateException(
                "Solo se puede resolver manualmente una reconciliación en estado MANUAL"
            );
        }
        Objects.requireNonNull(resolution, "La resolución no puede ser null");
        this.status = Status.MATCHED;
        this.manualResolution = resolution;
        this.matchedAt = Instant.now();
        this.updatedAt = Instant.now();
    }
    
    public boolean isIdempotentKeyUnique(Reconciliation other) {
        if (other == null) return true;
        return !this.eventId.equals(other.eventId) || this.version != other.version;
    }
    
    public String getIdempotencyKey() {
        return eventId + ":" + version;
    }
    
    public boolean isExpired(int windowMinutes) {
        if (status != Status.PENDING) {
            return false;
        }
        return createdAt.plusSeconds(windowMinutes * 60L).isBefore(Instant.now());
    }
    
    public UUID getId() { return id; }
    public String getEventId() { return eventId; }
    public int getVersion() { return version; }
    public String getSourceType() { return sourceType; }
    public String getTargetType() { return targetType; }
    public Status getStatus() { return status; }
    public BigDecimal getAmount() { return amount; }
    public String getCurrency() { return currency; }
    public String getReference() { return reference; }
    public String getDescription() { return description; }
    public Instant getMatchedAt() { return matchedAt; }
    public Instant getCreatedAt() { return createdAt; }
    public Instant getUpdatedAt() { return updatedAt; }
    public String getMismatchReason() { return mismatchReason; }
    public String getManualResolution() { return manualResolution; }
    
    public static class Builder {
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
        
        private Builder() {}
        
        public static Builder create() {
            return new Builder();
        }
        
        public Builder id(UUID id) { this.id = id; return this; }
        public Builder eventId(String eventId) { this.eventId = eventId; return this; }
        public Builder version(int version) { this.version = version; return this; }
        public Builder sourceType(String sourceType) { this.sourceType = sourceType; return this; }
        public Builder targetType(String targetType) { this.targetType = targetType; return this; }
        public Builder status(Status status) { this.status = status; return this; }
        public Builder amount(BigDecimal amount) { this.amount = amount; return this; }
        public Builder currency(String currency) { this.currency = currency; return this; }
        public Builder reference(String reference) { this.reference = reference; return this; }
        public Builder description(String description) { this.description = description; return this; }
        public Builder matchedAt(Instant matchedAt) { this.matchedAt = matchedAt; return this; }
        public Builder createdAt(Instant createdAt) { this.createdAt = createdAt; return this; }
        public Builder updatedAt(Instant updatedAt) { this.updatedAt = updatedAt; return this; }
        public Builder mismatchReason(String mismatchReason) { this.mismatchReason = mismatchReason; return this; }
        public Builder manualResolution(String manualResolution) { this.manualResolution = manualResolution; return this; }
        
        public Reconciliation build() {
            return new Reconciliation(this);
        }
    }
}