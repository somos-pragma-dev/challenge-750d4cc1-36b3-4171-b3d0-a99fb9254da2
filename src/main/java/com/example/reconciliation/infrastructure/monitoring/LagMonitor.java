package com.example.reconciliation.infrastructure.monitoring;

import com.example.reconciliation.domain.model.Reconciliation;
import com.example.reconciliation.domain.model.Reconciliation.Status;
import com.example.reconciliation.domain.repository.ReconciliationRepository;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;

@Component
public class LagMonitor {
    
    private static final Logger log = LoggerFactory.getLogger(LagMonitor.class);
    private static final Duration SLA_THRESHOLD = Duration.ofMinutes(5);
    private static final Duration CHECK_INTERVAL = Duration.ofSeconds(30);
    private static final Duration GRACE_PERIOD = Duration.ofSeconds(10);
    
    private final MeterRegistry meterRegistry;
    private final ReconciliationRepository reconciliationRepository;
    private final AtomicLong currentLagSeconds;
    private final AtomicReference<Instant> lastCheckTime;
    private final AtomicReference<Instant> lastAlertTime;
    private final AtomicReference<String> lagStatus;
    private final Counter lagExceededCounter;
    private final Counter lagNormalCounter;
    private final Timer lagCheckTimer;
    
    public LagMonitor(final MeterRegistry meterRegistry, 
                      final ReconciliationRepository reconciliationRepository) {
        this.meterRegistry = meterRegistry;
        this.reconciliationRepository = reconciliationRepository;
        this.currentLagSeconds = new AtomicLong(0);
        this.lastCheckTime = new AtomicReference<>(Instant.now());
        this.lastAlertTime = new AtomicReference<>(Instant.EPOCH);
        this.lagStatus = new AtomicReference<>("UNKNOWN");
        initializeMetrics();
    }
    
    private void initializeMetrics() {
        this.lagExceededCounter = Counter.builder("reconciliation.lag.exceeded")
            .description("Number of times SLA threshold was exceeded")
            .register(meterRegistry);
        
        this.lagNormalCounter = Counter.builder("reconciliation.lag.normal")
            .description("Number of times lag was within SLA")
            .register(meterRegistry);
        
        this.lagCheckTimer = Timer.builder("reconciliation.lag.check.duration")
            .description("Time taken to check lag metrics")
            .register(meterRegistry);
    }
    
    public void recordReconciliationCreated(final Instant createdAt) {
        log.debug("Recording reconciliation created at: {}", createdAt);
        meterRegistry.counter("reconciliation.created", "source", "lag_monitor")
            .increment();
    }
    
    public void recordReconciliationCompleted(final Instant matchedAt) {
        log.debug("Recording reconciliation completed at: {}", matchedAt);
        meterRegistry.counter("reconciliation.completed", "source", "lag_monitor")
            .increment();
        
        final Instant now = Instant.now();
        final long processingTime = Duration.between(matchedAt, now).getSeconds();
        meterRegistry.timer("reconciliation.processing.time")
            .record(Duration.ofSeconds(processingTime));
    }
    
    public void recordMismatch(final Instant updatedAt) {
        log.debug("Recording mismatch at: {}", updatedAt);
        meterRegistry.counter("reconciliation.mismatch", "source", "lag_monitor")
            .increment();
    }
    
    public Mono<LagMetrics> calculateCurrentLag() {
        return lagCheckTimer.record(() -> reconciliationRepository.findByStatus(Status.PENDING)
            .collectList()
            .map(this::calculateLagMetrics));
    }
    
    private LagMetrics calculateLagMetrics(final List<Reconciliation> pending) {
        if (pending.isEmpty()) {
            return new LagMetrics(0L, 0L, 0L, 0L, "NORMAL", Instant.now());
        }
        
        final Instant now = Instant.now();
        final Instant oldest = pending.stream()
            .map(Reconciliation::getCreatedAt)
            .min(Instant::compareTo)
            .orElse(now);
        
        final long lagSeconds = Duration.between(oldest, now).getSeconds();
        currentLagSeconds.set(lagSeconds);
        
        final String status = determineStatus(lagSeconds);
        lagStatus.set(status);
        
        final long pendingCount = pending.size();
        final long matchedCount = pending.stream()
            .filter(r -> r.getStatus() == Status.MATCHED)
            .count();
        final long mismatchedCount = pending.stream()
            .filter(r -> r.getStatus() == Status.MISMATCHED)
            .count();
        
        return new LagMetrics(lagSeconds, pendingCount, matchedCount, mismatchedCount, status, now);
    }
    
    private String determineStatus(final long lagSeconds) {
        final long thresholdSeconds = SLA_THRESHOLD.getSeconds();
        if (lagSeconds > thresholdSeconds) {
            lagExceededCounter.increment();
            handleSlaExceeded(null);
            return "EXCEEDED";
        } else {
            lagNormalCounter.increment();
            return "NORMAL";
        }
    }
    
    private void handleSlaExceeded(final LagMetrics metrics) {
        final Instant now = Instant.now();
        final Instant lastAlert = lastAlertTime.get();
        
        if (Duration.between(lastAlert, now).compareTo(CHECK_INTERVAL) > 0) {
            if (lastAlertTime.compareAndSet(lastAlert, now)) {
                triggerAlert(metrics);
            }
        }
    }
    
    private void triggerAlert(final LagMetrics metrics) {
        log.warn("SLA EXCEEDED: {}", metrics);
        meterRegistry.counter("reconciliation.sla.alerts").increment();
    }
    
    private void publishAlertMetric(final LagMetrics metrics) {
        meterRegistry.gauge("reconciliation.lag.current.seconds", currentLagSeconds);
        meterRegistry.gauge("reconciliation.lag.status", lagStatus, AtomicReference::get);
    }
    
    public Mono<Boolean> isLagAcceptable() {
        return calculateCurrentLag()
            .map(metrics -> metrics.lagSeconds() <= SLA_THRESHOLD.getSeconds());
    }
    
    public Mono<SlaComplianceReport> generateComplianceReport(final Duration window) {
        final Instant windowStart = Instant.now().minus(window);
        
        return reconciliationRepository.findByStatusAndCreatedAtAfter(Status.PENDING, windowStart)
            .collectList()
            .map(pending -> {
                final long maxLag = pending.stream()
                    .mapToLong(r -> Duration.between(r.getCreatedAt(), Instant.now()).getSeconds())
                    .max()
                    .orElse(0L);
                
                final String compliance = determineOverallCompliance(maxLag);
                return new SlaComplianceReport(window, pending.size(), maxLag, compliance);
            });
    }
    
    private String determineOverallCompliance(final long maxLagSeconds) {
        final long thresholdSeconds = SLA_THRESHOLD.getSeconds();
        if (maxLagSeconds <= thresholdSeconds) {
            return "COMPLIANT";
        } else if (maxLagSeconds <= thresholdSeconds * 2) {
            return "WARNING";
        } else {
            return "NON_COMPLIANT";
        }
    }
    
    public record LagMetrics(
        long lagSeconds,
        long pendingCount,
        long matchedCount,
        long mismatchedCount,
        String status,
        Instant timestamp
    ) {}
    
    public record StatusCount(Status status, long count) {}
    
    public record SlaComplianceReport(
        Duration window,
        long totalReconciliations,
        long maxLagSeconds,
        String complianceStatus
    ) {}
}