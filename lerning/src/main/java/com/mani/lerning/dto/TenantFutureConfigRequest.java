package com.mani.lerning.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TenantFutureConfigRequest {

    @NotBlank(message = "tenantId  must not be null")
    private String tenantId;

    @NotBlank(message = "future key must not be null")
    private String futureKey;

    @NotBlank(message = "config must not be null")
    private Map<String, Object> config;
}
