package com.example.reconciliation.domain.model;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

public final class Movement {
    
    public enum SourceType {
        CORE_BANKING("Core Bancario"),
        PAYMENT_GATEWAY("Gateway de Pagos"),
        LIQUIDATION_SYSTEM("Sistema de Liquidación");
        
        private final String displayName;
        
        SourceType(String displayName) {
            this.displayName = displayName;
        }
        
        public String getDisplayName() {
            return displayName;
        }
        
        public String name() {
            return super.name();
        }
    }
    
    public enum MovementType {
        CREDIT("Crédito"),
        DEBIT("Débito"),
        ADJUSTMENT("Ajuste");
        
        private final String displayName;
        
        MovementType(String displayName) {
            this.displayName = displayName;
        }
        
        public String getDisplayName() {
            return displayName;
        }
    }
    
    private final UUID id;
    private final String eventId;
    private final int version;
    private final SourceType sourceType;
    private final MovementType movementType;
    private final BigDecimal amount;
    private final String currency;
    private final String reference;
    private final String accountNumber;
    private final Instant timestamp;
    private final Map<String, String> metadata;
    private final Instant receivedAt;
    private final boolean processed;
    
    private Movement(Builder builder) {
        this.id = builder.id;
        this.eventId = builder.eventId;
        this.version = builder.version;
        this.sourceType = builder.sourceType;
        this.movementType = builder.movementType;
        this.amount = builder.amount;
        this.currency = builder.currency;
        this.reference = builder.reference;
        this.accountNumber = builder.accountNumber;
        this.timestamp = builder.timestamp;
        this.metadata = builder.metadata != null ? Map.copyOf(builder.metadata) : Map.of();
        this.receivedAt = builder.receivedAt;
        this.processed = builder.processed;
    }
    
    public static Movement create(String eventId, int version, SourceType sourceType,
            MovementType movementType, BigDecimal amount, String currency, String reference,
            String accountNumber, Instant timestamp, Map<String, String> metadata) {
        
        Objects.requireNonNull(eventId, "El eventId no puede ser null");
        Objects.requireNonNull(sourceType, "El sourceType no puede ser null");
        Objects.requireNonNull(movementType, "El movementType no puede ser null");
        Objects.requireNonNull(amount, "El amount no puede ser null");
        Objects.requireNonNull(currency, "El currency no puede ser null");
        Objects.requireNonNull(timestamp, "El timestamp no puede ser null");
        
        if (version < 0) {
            throw new IllegalArgumentException("La versión no puede ser negativa");
        }
        
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("El monto debe ser mayor que cero");
        }
        
        return Builder.create()
                .id(UUID.randomUUID())
                .eventId(eventId)
                .version(version)
                .sourceType(sourceType)
                .movementType(movementType)
                .amount(amount)
                .currency(currency)
                .reference(reference)
                .accountNumber(accountNumber)
                .timestamp(timestamp)
                .metadata(metadata)
                .receivedAt(Instant.now())
                .processed(false)
                .build();
    }
    
    public static Movement restore(UUID id, String eventId, int version, SourceType sourceType,
            MovementType movementType, BigDecimal amount, String currency, String reference,
            String accountNumber, Instant timestamp, Map<String, String> metadata,
            Instant receivedAt, boolean processed) {
        return Builder.create()
                .id(id)
                .eventId(eventId)
                .version(version)
                .sourceType(sourceType)
                .movementType(movementType)
                .amount(amount)
                .currency(currency)
                .reference(reference)
                .accountNumber(accountNumber)
                .timestamp(timestamp)
                .metadata(metadata)
                .receivedAt(receivedAt)
                .processed(processed)
                .build();
    }
    
    public boolean isDuplicate(Movement other) {
        if (other == null) return false;
        return this.eventId.equals(other.eventId) && 
               this.version == other.version && 
               this.sourceType == other.sourceType;
    }
    
    public boolean isOutOfOrder(Instant windowStart) {
        return timestamp.isBefore(windowStart);
    }
    
    public String getIdempotencyKey() {
        return sourceType.name() + ":" + eventId + ":" + version;
    }
    
    public boolean matchesAmount(BigDecimal expectedAmount) {
        return this.amount.compareTo(expectedAmount) == 0;
    }
    
    public boolean matchesReference(String expectedReference) {
        if (expectedReference == null) return false;
        return this.reference.equals(expectedReference);
    }
    
    public boolean matchesAccount(String expectedAccount) {
        if (expectedAccount == null) return false;
        return this.accountNumber.equals(expectedAccount);
    }
    
    public boolean isWithinTimeWindow(Instant windowStart, Instant windowEnd) {
        return !timestamp.isBefore(windowStart) && !timestamp.isAfter(windowEnd);
    }
    
    public UUID getId() { return id; }
    public String getEventId() { return eventId; }
    public int getVersion() { return version; }
    public SourceType getSourceType() { return sourceType; }
    public MovementType getMovementType() { return movementType; }
    public BigDecimal getAmount() { return amount; }
    public String getCurrency() { return currency; }
    public String getReference() { return reference; }
    public String getAccountNumber() { return accountNumber; }
    public Instant getTimestamp() { return timestamp; }
    public Map<String, String> getMetadata() { return metadata; }
    public Instant getReceivedAt() { return receivedAt; }
    public boolean isProcessed() { return processed; }
    
    public static class Builder {
        private UUID id;
        private String eventId;
        private int version;
        private SourceType sourceType;
        private MovementType movementType;
        private BigDecimal amount;
        private String currency;
        private String reference;
        private String accountNumber;
        private Instant timestamp;
        private Map<String, String> metadata;
        private Instant receivedAt;
        private boolean processed;
        
        private Builder() {}
        
        public static Builder create() {
            return new Builder();
        }
        
        public Builder id(UUID id) { this.id = id; return this; }
        public Builder eventId(String eventId) { this.eventId = eventId; return this; }
        public Builder version(int version) { this.version = version; return this; }
        public Builder sourceType(SourceType sourceType) { this.sourceType = sourceType; return this; }
        public Builder movementType(MovementType movementType) { this.movementType = movementType; return this; }
        public Builder amount(BigDecimal amount) { this.amount = amount; return this; }
        public Builder currency(String currency) { this.currency = currency; return this; }
        public Builder reference(String reference) { this.reference = reference; return this; }
        public Builder accountNumber(String accountNumber) { this.accountNumber = accountNumber; return this; }
        public Builder timestamp(Instant timestamp) { this.timestamp = timestamp; return this; }
        public Builder metadata(Map<String, String> metadata) { this.metadata = metadata; return this; }
        public Builder receivedAt(Instant receivedAt) { this.receivedAt = receivedAt; return this; }
        public Builder processed(boolean processed) { this.processed = processed; return this; }
        
        public Movement build() {
            return new Movement(this);
        }
    }
}