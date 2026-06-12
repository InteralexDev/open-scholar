package io.github.interalexdev.openscholar.mapper;

import io.github.interalexdev.openscholar.dto.openalex.OpenAlexWorkDto;
import io.github.interalexdev.openscholar.model.PublicationMetadata;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class OpenAlexMapper {

    public PublicationMetadata toPublicationMetadata(OpenAlexWorkDto work) {
        return new PublicationMetadata(
                work.id(),
                work.doi(),
                work.displayName(),
                work.publicationYear(),
                work.publicationDate(),
                work.type(),
                mapAuthors(work),
                getJournalName(work),
                getPublisher(work),
                work.language(),
                mapKeywords(work),
                mapTopics(work),
                work.citedByCount(),
                getSourceUrl(work)
        );
    }

    private List<String> mapAuthors(OpenAlexWorkDto work) {
        if (work.authorships() == null) {
            return List.of();
        }

        return work.authorships().stream()
                .map(authorship -> authorship.author())
                .filter(author -> author != null)
                .map(author -> author.displayName())
                .toList();
    }

    private List<String> mapKeywords(OpenAlexWorkDto work) {
        if (work.keywords() == null) {
            return List.of();
        }

        return work.keywords().stream()
                .map(keyword -> keyword.displayName())
                .toList();
    }

    private List<String> mapTopics(OpenAlexWorkDto work) {
        if (work.topics() == null) {
            return List.of();
        }

        return work.topics().stream()
                .map(topic -> topic.displayName())
                .toList();
    }

    private String getJournalName(OpenAlexWorkDto work) {
        if (work.primaryLocation() == null || work.primaryLocation().source() == null) {
            return null;
        }

        return work.primaryLocation().source().displayName();
    }

    private String getPublisher(OpenAlexWorkDto work) {
        if (work.primaryLocation() == null || work.primaryLocation().source() == null) {
            return null;
        }

        return work.primaryLocation().source().hostOrganizationName();
    }

    private String getSourceUrl(OpenAlexWorkDto work) {
        if (work.primaryLocation() == null) {
            return null;
        }

        return work.primaryLocation().landingPageUrl();
    }
}