package io.github.interalexdev.openscholar.dto.openalex;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
public record OpenAlexWorkDto(

        String id,

        String doi,

        String title,

        @JsonProperty("display_name")
        String displayName,

        @JsonProperty("publication_year")
        Integer publicationYear,

        @JsonProperty("publication_date")
        String publicationDate,

        String language,

        String type,

        @JsonProperty("primary_location")
        OpenAlexPrimaryLocationDto primaryLocation,

        List<OpenAlexAuthorshipDto> authorships,

        @JsonProperty("cited_by_count")
        Integer citedByCount,

        List<OpenAlexTopicDto> topics,

        List<OpenAlexKeywordDto> keywords,

        @JsonProperty("abstract_inverted_index")
        Map<String, List<Integer>> abstractInvertedIndex

) {
}