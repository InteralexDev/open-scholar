package io.github.interalexdev.openscholar.dto.openalex;

import com.fasterxml.jackson.annotation.JsonProperty;

public record OpenAlexTopicDto(

        String id,

        @JsonProperty("display_name")
        String displayName,

        Double score

) {
}