package io.github.interalexdev.openscholar.dto.openalex;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public record OpenAlexSourceDto(

        String id,

        @JsonProperty("display_name")
        String displayName,

        @JsonProperty("host_organization_name")
        String hostOrganizationName,

        String type

) {
}