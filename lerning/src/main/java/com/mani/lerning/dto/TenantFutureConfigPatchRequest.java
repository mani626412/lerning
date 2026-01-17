package com.mani.lerning.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TenantFutureConfigPatchRequest {

    @NotNull(message = "config must not be null")
    private Map<String, Object> config;
}
