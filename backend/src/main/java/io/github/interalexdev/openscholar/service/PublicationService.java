package io.github.interalexdev.openscholar.service;

import io.github.interalexdev.openscholar.client.OpenAlexClient;
import io.github.interalexdev.openscholar.dto.openalex.OpenAlexSearchResponse;
import org.springframework.stereotype.Service;

@Service
public class PublicationService {

    private final OpenAlexClient openAlexClient;

    public PublicationService(OpenAlexClient openAlexClient) {
        this.openAlexClient = openAlexClient;
    }

    public OpenAlexSearchResponse searchPublications(String query) {
        return openAlexClient.searchWorks(query);
    }
}