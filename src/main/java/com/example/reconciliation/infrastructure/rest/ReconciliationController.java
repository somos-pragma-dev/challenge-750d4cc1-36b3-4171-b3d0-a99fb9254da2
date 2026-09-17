package com.example.reconciliation.infrastructure.rest;

import com.example.reconciliation.application.services.ReconciliationService;
import com.example.reconciliation.domain.model.Reconciliation;
import com.example.reconciliation.domain.model.Reconciliation.Status;
import com.example.reconciliation.domain.repository.ReconciliationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Instant;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/reconciliations")
public class ReconciliationController {

    private static final Logger log = LoggerFactory.getLogger(ReconciliationController.class);
    private static final int DEFAULT_PAGE_SIZE = 50;
    private static final int MAX_PAGE_SIZE = 500;

    private final ReconciliationService reconciliationService;
    private final ReconciliationRepository reconciliationRepository;

    public ReconciliationController(
            ReconciliationService reconciliationService,
            ReconciliationRepository reconciliationRepository) {
        this.reconciliationService = reconciliationService;
        this.reconciliationRepository = reconciliationRepository;
    }

    @GetMapping("/{id}")
    public Mono<ResponseEntity<ReconciliationResponse>> getById(@PathVariable UUID id) {
        log.debug("Fetching reconciliation by id: {}", id);
        return reconciliationRepository.findById(id)
            .map(reconciliation -> ResponseEntity.ok(toResponse(reconciliation)))
            .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @GetMapping
    public Mono<ReconciliationPageResponse> getAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "50") int size) {
        
        int pageSize = Math.min(Math.max(size, 1), MAX_PAGE_SIZE);
        log.debug("Fetching reconciliations - page: {}, size: {}", page, pageSize);
        
        return reconciliationRepository.findAll(page, pageSize)
            .collectList()
            .zipWith(reconciliationRepository.count())
            .map(tuple -> new ReconciliationPageResponse(
                tuple.getT1().stream().map(this::toResponse).toList(),
                page,
                pageSize,
                tuple.getT2()
            ));
    }

    @GetMapping("/status/{status}")
    public Flux<ReconciliationResponse> getByStatus(@PathVariable Status status) {
        log.debug("Fetching reconciliations by status: {}", status);
        return reconciliationRepository.findByStatus(status)
            .map(this::toResponse);
    }

    @GetMapping("/pending")
    public Flux<ReconciliationResponse> getPending(
            @RequestParam(defaultValue = "300") int windowSeconds) {
        
        Instant cutoff = Instant.now().minusSeconds(windowSeconds);
        log.debug("Fetching pending reconciliations older than {} seconds", windowSeconds);
        
        return reconciliationRepository.findPendingOlderThan(cutoff)
            .map(this::toResponse);
    }

    @GetMapping("/statistics")
    public Mono<Map<String, Long>> getStatistics() {
        log.debug("Fetching reconciliation statistics");
        return reconciliationService.getStatistics();
    }

    @PostMapping("/{id}/match")
    public Mono<ResponseEntity<ReconciliationResponse>> matchReconciliation(
            @PathVariable UUID id) {
        
        log.info("Manual match requested for reconciliation: {}", id);
        return reconciliationService.manualMatch(id)
            .map(reconciliation -> ResponseEntity.ok(toResponse(reconciliation)))
            .defaultIfEmpty(ResponseEntity.notFound().build())
            .onErrorResume(e -> {
                log.error("Error matching reconciliation {}: {}", id, e.getMessage());
                return Mono.just(ResponseEntity.badRequest().build());
            });
    }

    @PostMapping("/{id}/resolve")
    public Mono<ResponseEntity<ReconciliationResponse>> resolveReconciliation(
            @PathVariable UUID id,
            @RequestBody ResolveRequest request) {
        
        log.info("Manual resolution requested for reconciliation: {} with resolution: {}", 
            id, request.resolution());
        return reconciliationService.resolveManually(id, request.resolution())
            .map(reconciliation -> ResponseEntity.ok(toResponse(reconciliation)))
            .defaultIfEmpty(ResponseEntity.notFound().build())
            .onErrorResume(e -> {
                log.error("Error resolving reconciliation {}: {}", id, e.getMessage());
                return Mono.just(ResponseEntity.badRequest().build());
            });
    }

    @PutMapping("/{id}/escalate")
    public Mono<ResponseEntity<ReconciliationResponse>> escalateToManual(
            @PathVariable UUID id,
            @RequestBody EscalateRequest request) {
        
        log.info("Escalation to manual for reconciliation: {} with reason: {}", 
            id, request.reason());
        return reconciliationService.escalateToManual(id, request.reason())
            .map(reconciliation -> ResponseEntity.ok(toResponse(reconciliation)))
            .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @GetMapping("/search/by-reference")
    public Flux<ReconciliationResponse> searchByReference(
            @RequestParam String reference,
            @RequestParam(required = false) String sourceType,
            @RequestParam(required = false) String targetType) {
        
        log.debug("Searching reconciliations by reference: {}, sourceType: {}, targetType: {}",
            reference, sourceType, targetType);
        
        return reconciliationService.searchByReference(reference, sourceType, targetType)
            .map(this::toResponse);
    }

    @PostMapping("/reprocess")
    public Mono<ResponseEntity<String>> reprocess(
            @RequestBody ReprocessRequest request) {
        
        log.info("Reprocess requested for eventId: {}, version: {}", 
            request.eventId(), request.version());
        return reconciliationService.reprocess(request.eventId(), request.version())
            .map(r -> ResponseEntity.ok("Reprocess initiated for " + request.eventId()))
            .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    private ReconciliationResponse toResponse(Reconciliation reconciliation) {
        return new ReconciliationResponse(
            reconciliation.getId(),
            reconciliation.getEventId(),
            reconciliation.getVersion(),
            reconciliation.getSourceType(),
            reconciliation.getTargetType(),
            reconciliation.getStatus().name(),
            reconciliation.getAmount(),
            reconciliation.getCurrency(),
            reconciliation.getReference(),
            reconciliation.getDescription(),
            reconciliation.getMatchedAt(),
            reconciliation.getCreatedAt(),
            reconciliation.getUpdatedAt(),
            reconciliation.getMismatchReason(),
            reconciliation.getManualResolution()
        );
    }

    public record ReconciliationResponse(
        UUID id,
        String eventId,
        int version,
        String sourceType,
        String targetType,
        String status,
        java.math.BigDecimal amount,
        String currency,
        String reference,
        String description,
        Instant matchedAt,
        Instant createdAt,
        Instant updatedAt,
        String mismatchReason,
        String manualResolution
    ) {}

    public record ReconciliationPageResponse(
        java.util.List<ReconciliationResponse> items,
        int page,
        int size,
        long total
    ) {}

    public record ResolveRequest(String resolution) {}
    public record EscalateRequest(String reason) {}
    public record ReprocessRequest(String eventId, int version, String reason, String resolution) {}
}