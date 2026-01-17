package com.mani.lerning.service;

import com.mani.lerning.dto.TenantFutureConfigRequest;
import com.mani.lerning.dto.TenantFutureConfigResponse;
import com.mani.lerning.entity.TenantFutureConfig;
import com.mani.lerning.repository.TenantFutureConfigRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class TenantConfigFutureService {
    private final TenantFutureConfigRepository tenantFutureConfigRepository;

    public TenantConfigFutureService(TenantFutureConfigRepository tenantFutureConfigRepository) {
        this.tenantFutureConfigRepository = tenantFutureConfigRepository;
    }

    @Transactional
    public TenantFutureConfigResponse create(TenantFutureConfigRequest request) {

        validateCreateRequest(request);
        boolean exists = tenantFutureConfigRepository.existsByTenantIdAndFutureKeyAndIsActiveTrue(request.getTenantId(), request.getFutureKey());

        if (exists) {
            throw new IllegalArgumentException("Active config allready exist by tenant =" + request.getTenantId() + "futureKey, " + request.getFutureKey());
        }
        TenantFutureConfig entity = new TenantFutureConfig();

        entity.setTenantId(request.getTenantId());
        entity.setFutureKey(request.getFutureKey());
        entity.setConfig(request.getConfig());
        entity.setIsActive(true);
        entity.setCreatedBy("SYSTEM");
        entity.setUpdatedBy("SYSTEM");

        TenantFutureConfig saved = tenantFutureConfigRepository.save(entity);

        return toResponse(saved);

    }

    public TenantFutureConfigResponse getActiveConfig(String tenantId, String futureKey) {
        if (tenantId == null || futureKey == null) {
            throw new IllegalStateException("tenantId and futureKey must not be empty");
        }
        TenantFutureConfig entity = tenantFutureConfigRepository.findByTenantIdAndFutureKeyAndIsActiveTrue(tenantId, futureKey).orElseThrow(() ->
                new IllegalStateException
                        ("Active config not found for tenant=" + tenantId + ", futureKey=" + futureKey));

        return toResponse(entity);

    }

    public List<TenantFutureConfigResponse> getAllActiveConfig(String tenantId) {
        if (StringUtils.isEmpty(tenantId)) {
            throw new IllegalArgumentException("tenantId must not be empty");
        }
        return tenantFutureConfigRepository.findAllByTenantIdAndIsActiveTrue(tenantId)
                .stream()
                .map(this::toResponse)
                .toList();

    }

    public TenantFutureConfigResponse update(String tenantId, String futureKey, TenantFutureConfigRequest request) {
        if (ObjectUtils.isEmpty(request)) {
            throw new IllegalStateException("request must not be empty");
        }
        TenantFutureConfig entity = tenantFutureConfigRepository.findByTenantIdAndFutureKeyAndIsActiveTrue(tenantId, futureKey).
                orElseThrow(() -> new IllegalStateException("tenantId " + tenantId + "futureKey" + futureKey + "must not be empty"));

        entity.setConfig(request.getConfig());
        entity.setUpdatedBy("System");

        return toResponse(tenantFutureConfigRepository.save(entity));
    }

    public void softDelete(String tenantId, String futureKey) {
        if (tenantId == null || futureKey == null) {
            throw new IllegalStateException("tenantId and futureKey must not be empty");
        }
        TenantFutureConfig entity = tenantFutureConfigRepository.findByTenantIdAndFutureKeyAndIsActiveTrue(tenantId, futureKey).orElseThrow(() -> new IllegalStateException("Active config not found for tenant=" + tenantId + ", futureKey=" + futureKey));
        tenantFutureConfigRepository.softDelete(tenantId, futureKey);
    }

    public TenantFutureConfigResponse patchConfig(String tenantId, String futureKey, Map<String, Object> patchConfig) {

        if (patchConfig == null || patchConfig.isEmpty()) {
            throw new IllegalArgumentException("Patch config cannot be empty");
        }

        TenantFutureConfig entity =
                tenantFutureConfigRepository.findByTenantIdAndFutureKeyAndIsActiveTrue(tenantId, futureKey)
                        .orElseThrow(() -> new IllegalStateException("Active config not found for tenant=" + tenantId + ", futureKey=" + futureKey));

        mergeConfig(entity.getConfig(), patchConfig);
        entity.setUpdatedBy("SYSTEM");

        TenantFutureConfig saved = tenantFutureConfigRepository.save(entity);
        return toResponse(saved);
    }

    @SuppressWarnings("unchecked")
    private void mergeConfig(Map<String, Object> existing, Map<String, Object> incoming) {

        for (Map.Entry<String, Object> entry : incoming.entrySet()) {

            String key = entry.getKey();
            Object value = entry.getValue();

            if (value instanceof Map
                    && existing.get(key) instanceof Map) {

                mergeConfig((Map<String, Object>) existing.get(key), (Map<String, Object>) value);

            } else {
                existing.put(key, value);
            }
        }
    }

    private TenantFutureConfigResponse toResponse(TenantFutureConfig entity) {

        TenantFutureConfigResponse response = new TenantFutureConfigResponse();

        response.setId(entity.getId());
        response.setTenantId(entity.getTenantId());
        response.setFutureKey(entity.getFutureKey());
        response.setIsActive(entity.getIsActive());
        response.setCreatedBy(entity.getCreatedBy());
        response.setUpdatedBy(entity.getUpdatedBy());
        response.setCreatedAt(entity.getCreatedAt());
        response.setUpdatedAt(entity.getUpdatedAt());

        return response;
    }

    private void validateCreateRequest(TenantFutureConfigRequest request) {

        if (request.getTenantId() == null || request.getTenantId().isBlank()) {
            throw new IllegalArgumentException("Tenant is required");
        }
        if (request.getFutureKey() == null || request.getFutureKey().isBlank()) {
            throw new IllegalArgumentException("Future key required");
        }
        if (request.getConfig() == null || request.getConfig().isEmpty()) {
            throw new IllegalArgumentException("Config must not be empty");
        }
    }


}
