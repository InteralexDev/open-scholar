package io.github.interalexdev.openscholar.dto.openalex;

import com.fasterxml.jackson.annotation.JsonProperty;

public record OpenAlexPrimaryLocationDto(

        @JsonProperty("landing_page_url")
        String landingPageUrl,

        OpenAlexSourceDto source

) {
}
