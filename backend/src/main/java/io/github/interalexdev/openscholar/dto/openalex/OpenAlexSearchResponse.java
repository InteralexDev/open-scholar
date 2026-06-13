package io.github.interalexdev.openscholar.dto.openalex;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record OpenAlexSearchResponse(
        List<OpenAlexWorkDto> results
) {
}
