package io.github.interalexdev.openscholar.dto.openalex;

import com.fasterxml.jackson.annotation.JsonProperty;

public record OpenAlexAuthorDto(

        String id,

        @JsonProperty("display_name")
        String displayName,

        String orcid

) {
}