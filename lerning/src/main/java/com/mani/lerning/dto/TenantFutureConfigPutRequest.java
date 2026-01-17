package com.mani.lerning.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TenantFutureConfigPutRequest {


    @NotNull(message = "config must not be null")
    private Map<String, Object> config;

}
