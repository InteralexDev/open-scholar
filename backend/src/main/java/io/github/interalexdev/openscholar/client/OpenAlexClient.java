package io.github.interalexdev.openscholar.client;

import com.fasterxml.jackson.databind.json.JsonMapper;
import io.github.interalexdev.openscholar.dto.openalex.OpenAlexSearchResponse;
import io.github.interalexdev.openscholar.dto.openalex.OpenAlexWorkDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

/**
 * Client responsible for communicating with the OpenAlex REST API.
 */
@Component
public class OpenAlexClient {

    private static final String WORKS_PATH = "/works";
    private static final int SEARCH_RESULTS_LIMIT = 10;

    private final RestClient restClient;

    public OpenAlexClient(
            RestClient.Builder restClientBuilder,
            @Value("${openalex.base-url}") String baseUrl
    ) {
        this.restClient = restClientBuilder
                .baseUrl(baseUrl)
                .defaultHeader(
                        "User-Agent",
                        "OpenScholar (https://github.com/interalexDev/open-scholar)"
                )
                .messageConverters(converters -> {
                    converters.removeIf(MappingJackson2HttpMessageConverter.class::isInstance);
                    converters.add(new MappingJackson2HttpMessageConverter(JsonMapper.builder().build()));
                })
                .build();
    }

    public OpenAlexSearchResponse searchWorks(String query) {
        return restClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path(WORKS_PATH)
                        .queryParam("search", query)
                        .queryParam("per-page", SEARCH_RESULTS_LIMIT)
                        .build())
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .body(OpenAlexSearchResponse.class);
    }

    public OpenAlexWorkDto getPublicationDetails(String openAlexId) {
        return restClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path(WORKS_PATH + "/{id}")
                        .build(openAlexId))
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .body(OpenAlexWorkDto.class);
    }
}