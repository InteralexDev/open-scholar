package io.github.interalexdev.openscholar.mapper;

import io.github.interalexdev.openscholar.entity.ViewedPublication;
import io.github.interalexdev.openscholar.model.PublicationMetadata;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class ViewedPublicationMapper {

    public ViewedPublication toViewedPublication(PublicationMetadata metadata) {
        ViewedPublication publication = new ViewedPublication();

        publication.setOpenAlexId(metadata.openAlexId());
        publication.setDoi(metadata.doi());
        publication.setTitle(metadata.title());
        publication.setPublicationYear(metadata.publicationYear());
        publication.setPublicationDate(metadata.publicationDate());
        publication.setType(metadata.type());
        publication.setAuthors(metadata.authors());
        publication.setJournalName(metadata.journalName());
        publication.setPublisher(metadata.publisher());
        publication.setLanguage(metadata.language());
        publication.setKeywords(metadata.keywords());
        publication.setTopics(metadata.topics());
        publication.setCitedByCount(metadata.citedByCount());
        publication.setSourceUrl(metadata.sourceUrl());
        publication.setViewedAt(LocalDateTime.now());

        return publication;
    }
}