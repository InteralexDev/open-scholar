package io.github.interalexdev.openscholar.mapper;

import io.github.interalexdev.openscholar.dto.openalex.OpenAlexWorkDto;
import io.github.interalexdev.openscholar.model.PublicationMetadata;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DublinCoreMapperTest {

    private final OpenAlexMapper mapper = new OpenAlexMapper();

    @Test
    void toPublicationMetadata_shouldMapOpenAlexWorkToPublicationMetadata() {

        OpenAlexWorkDto work = new OpenAlexWorkDto(
                "https://openalex.org/W123",
                "https://doi.org/10.123/example",
                "Ignored title",
                "Display title",
                2024,
                "2024-01-01",
                "en",
                "article",
                null,
                null,
                42,
                null,
                null,
                null
        );

        PublicationMetadata result = mapper.toPublicationMetadata(work);

        assertEquals("https://openalex.org/W123", result.openAlexId());
        assertEquals("https://doi.org/10.123/example", result.doi());
        assertEquals("Display title", result.title());
        assertEquals(2024, result.publicationYear());
        assertEquals("2024-01-01", result.publicationDate());
        assertEquals("en", result.language());
        assertEquals("article", result.type());
        assertEquals(42, result.citedByCount());

        assertTrue(result.authors().isEmpty());
        assertTrue(result.keywords().isEmpty());
        assertTrue(result.topics().isEmpty());

        assertNull(result.journalName());
        assertNull(result.publisher());
        assertNull(result.sourceUrl());
    }

    @Test
    void toPublicationMetadata_shouldHandleNullValues() {

        OpenAlexWorkDto work = new OpenAlexWorkDto(
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null
        );

        PublicationMetadata result = mapper.toPublicationMetadata(work);

        assertNull(result.openAlexId());
        assertNull(result.doi());
        assertNull(result.title());
        assertNull(result.publicationYear());
        assertNull(result.publicationDate());
        assertNull(result.language());
        assertNull(result.type());
        assertNull(result.citedByCount());

        assertTrue(result.authors().isEmpty());
        assertTrue(result.keywords().isEmpty());
        assertTrue(result.topics().isEmpty());

        assertNull(result.journalName());
        assertNull(result.publisher());
        assertNull(result.sourceUrl());
    }
}