package com.mani.lerning.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class PaginatedResponse {

    @JsonProperty("content")
    private List<?> content;

    @JsonProperty("totalElement")
    private Long totalElement;

    @JsonProperty("totalPage")
    private int totalPage;

    @JsonProperty("limit")
    private int limit;

    @JsonProperty("offSet")
    private int offSet;

    @JsonProperty("order")
    private String order;

    @JsonProperty("orderBy")
    private String orderBy;
}
