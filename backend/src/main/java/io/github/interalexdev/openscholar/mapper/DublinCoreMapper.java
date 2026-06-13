package io.github.interalexdev.openscholar.mapper;

import io.github.interalexdev.openscholar.model.PublicationMetadata;
import io.github.interalexdev.openscholar.model.export.DublinCoreDocument;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Maps OpenAlex publication metadata to a Dublin Core representation
 * suitable for XML export.
 */
@Component
public class DublinCoreMapper {

    public DublinCoreDocument toDublinCoreDocument(PublicationMetadata metadata) {
        List<String> subjects = new ArrayList<>();

        if (metadata.keywords() != null) {
            subjects.addAll(metadata.keywords());
        }

        if (metadata.topics() != null) {
            subjects.addAll(metadata.topics());
        }

        return new DublinCoreDocument(
                "http://www.openarchives.org/OAI/2.0/oai_dc/",
                "http://purl.org/dc/elements/1.1/",
                "http://www.w3.org/2001/XMLSchema-instance",
                "http://www.openarchives.org/OAI/2.0/oai_dc/ http://www.openarchives.org/OAI/2.0/oai_dc.xsd",
                metadata.title(),
                metadata.authors(),
                metadata.publisher(),
                metadata.publicationDate(),
                metadata.type(),
                metadata.language(),
                metadata.doi() != null ? metadata.doi() : metadata.openAlexId(),
                subjects,
                metadata.sourceUrl()
        );
    }
}