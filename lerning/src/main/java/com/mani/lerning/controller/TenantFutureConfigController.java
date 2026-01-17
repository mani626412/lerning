package com.mani.lerning.controller;

import com.mani.lerning.dto.TenantFutureConfigRequest;
import com.mani.lerning.dto.TenantFutureConfigResponse;
import com.mani.lerning.service.TenantConfigFutureService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/tenant-future-configs")
@RequiredArgsConstructor
public class TenantFutureConfigController {
    private TenantConfigFutureService service;

    @PostMapping
    private ResponseEntity<TenantFutureConfigResponse> createConfig(@RequestBody TenantFutureConfigRequest request) {

        return ResponseEntity.ok().body(service.create(request));

    }

    @GetMapping("/{tenantId}/{futureKey}")
    private ResponseEntity<TenantFutureConfigResponse> getConfig(@PathVariable String tenantId,
                                                                 @PathVariable String futureKey) {
        return ResponseEntity.ok(service.getActiveConfig(tenantId, futureKey));
    }

    @GetMapping("/{tenantId}")
    private ResponseEntity<List<TenantFutureConfigResponse>> getAllConfig(@PathVariable String tenantId) {
        return ResponseEntity.ok(service.getAllActiveConfig(tenantId));
    }

    @PutMapping("/{tenantId}/{futureKey}")
    private ResponseEntity<TenantFutureConfigResponse> updateConfig(@PathVariable String tenantId,
                                                                    @PathVariable String futureKey,
                                                                    @RequestBody TenantFutureConfigRequest request) {
        return ResponseEntity.ok(service.update(tenantId, futureKey, request));
    }

    @DeleteMapping("/{tenantId}/{futureKey}")
    public ResponseEntity<Map<String, String>> softDelete(
            @PathVariable String tenantId,
            @PathVariable String futureKey) {
        service.softDelete(tenantId, futureKey);
        return ResponseEntity.ok(Map.of("message", "Soft delete completed successfully"));
    }

    @PatchMapping("/{tenantId}/{futureKey}")
    public ResponseEntity<TenantFutureConfigResponse> patch(
            @PathVariable String tenantId,
            @PathVariable String futureKey,
            @RequestBody Map<String, Object> patchConfig) {
        return ResponseEntity.ok(service.patchConfig(tenantId, futureKey, patchConfig));
    }
}
