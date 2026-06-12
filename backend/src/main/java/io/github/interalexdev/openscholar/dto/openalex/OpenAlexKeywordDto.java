package io.github.interalexdev.openscholar.dto.openalex;

import com.fasterxml.jackson.annotation.JsonProperty;

public record OpenAlexKeywordDto(

        String id,

        @JsonProperty("display_name")
        String displayName,

        Double score

) {
}