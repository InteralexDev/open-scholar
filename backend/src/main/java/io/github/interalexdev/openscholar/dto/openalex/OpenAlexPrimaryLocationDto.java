package io.github.interalexdev.openscholar.dto.openalex;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public record OpenAlexPrimaryLocationDto(

        @JsonProperty("landing_page_url")
        String landingPageUrl,

        OpenAlexSourceDto source

) {
}
