package io.github.interalexdev.openscholar.service;

import io.github.interalexdev.openscholar.client.OpenAlexClient;
import io.github.interalexdev.openscholar.dto.openalex.OpenAlexWorkDto;
import io.github.interalexdev.openscholar.mapper.OpenAlexMapper;
import io.github.interalexdev.openscholar.model.PublicationMetadata;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PublicationService {

    private final OpenAlexClient openAlexClient;
    private final OpenAlexMapper openAlexMapper;
    private final ViewedPublicationService viewedPublicationService;

    public PublicationService(OpenAlexClient openAlexClient, OpenAlexMapper openAlexMapper, ViewedPublicationService viewedPublicationService) {
        this.openAlexClient = openAlexClient;
        this.openAlexMapper = openAlexMapper;
        this.viewedPublicationService = viewedPublicationService;
    }

    public List<PublicationMetadata> searchPublications(String query) {
        return openAlexClient.searchWorks(query)
                .results()
                .stream()
                .map(openAlexMapper::toPublicationMetadata)
                .toList();
    }

    public PublicationMetadata getPublicationDetails(String openAlexId) {
        OpenAlexWorkDto work = openAlexClient.getPublicationDetails(openAlexId);

        PublicationMetadata publication =
                openAlexMapper.toPublicationMetadata(work);

        viewedPublicationService.saveOrUpdate(publication);

        return publication;
    }

    public PublicationMetadata exportPublicationJson(String openAlexId) {
        OpenAlexWorkDto dto = openAlexClient.getPublicationDetails(openAlexId);
        return openAlexMapper.toPublicationMetadata(dto);
    }

}