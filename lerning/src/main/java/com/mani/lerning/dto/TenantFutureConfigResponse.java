package com.mani.lerning.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TenantFutureConfigResponse {

    private Long id;

    private String tenantId;

    private String futureKey;

    private Map<String, Object> config;

    private Boolean isActive;

    private Long version;

    private String createdBy;

    private String updatedBy;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
