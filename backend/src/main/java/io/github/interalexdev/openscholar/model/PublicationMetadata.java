package io.github.interalexdev.openscholar.model;

import java.util.List;

public record PublicationMetadata(

        String openAlexId,
        String doi,
        String title,
        Integer publicationYear,
        String publicationDate,
        String type,
        List<String> authors,
        String journalName,
        String publisher,
        String language,
        List<String> keywords,
        List<String> topics,
        Integer citedByCount,
        String sourceUrl

) {
}