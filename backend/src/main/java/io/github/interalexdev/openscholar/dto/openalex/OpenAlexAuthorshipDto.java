package io.github.interalexdev.openscholar.dto.openalex;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record OpenAlexAuthorshipDto(

        OpenAlexAuthorDto author

) {
}