package io.github.interalexdev.openscholar.client;

import io.github.interalexdev.openscholar.dto.openalex.OpenAlexSearchResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

/**
 * Client responsible for communicating with the OpenAlex REST API.
 */
@Component
public class OpenAlexClient {

    private static final String WORKS_PATH = "/works";

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
                .build();
    }

    public OpenAlexSearchResponse searchWorks(String query) {
        return restClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/works")
                        .queryParam("search", query)
                        .queryParam("per-page", 10)
                        .build())
                .retrieve()
                .body(OpenAlexSearchResponse.class);
    }
}