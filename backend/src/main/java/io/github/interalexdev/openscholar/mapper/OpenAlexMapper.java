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
                List.of(),
                null,
                null,
                work.language(),
                List.of(),
                List.of(),
                work.citedByCount(),
                null
        );
    }
}