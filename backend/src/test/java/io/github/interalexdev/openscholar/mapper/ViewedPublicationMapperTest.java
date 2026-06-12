package io.github.interalexdev.openscholar.mapper;

import io.github.interalexdev.openscholar.entity.ViewedPublication;
import io.github.interalexdev.openscholar.mapper.ViewedPublicationMapper;
import io.github.interalexdev.openscholar.model.PublicationMetadata;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ViewedPublicationMapperTest {

    private final ViewedPublicationMapper mapper = new ViewedPublicationMapper();

    @Test
    void toViewedPublication_shouldMapMetadataToEntity() {
        PublicationMetadata metadata = new PublicationMetadata(
                "https://openalex.org/W123",
                "https://doi.org/10.123/example",
                "Test publication",
                2024,
                "2024-01-01",
                "article",
                List.of("Author One", "Author Two"),
                "Test Journal",
                "Test Publisher",
                "en",
                List.of("Java", "Spring"),
                List.of("Software Engineering"),
                42,
                "https://example.com/source"
        );

        ViewedPublication result = mapper.toViewedPublication(metadata);

        assertEquals(metadata.openAlexId(), result.getOpenAlexId());
        assertEquals(metadata.title(), result.getTitle());
        assertEquals(metadata.authors(), result.getAuthors());
        assertEquals(metadata.keywords(), result.getKeywords());
        assertEquals(metadata.topics(), result.getTopics());
        assertNotNull(result.getViewedAt());
    }

    @Test
    void toPublicationMetadata_shouldMapEntityToMetadata() {
        ViewedPublication publication = new ViewedPublication(
                1L,
                "https://openalex.org/W123",
                "https://doi.org/10.123/example",
                "Test publication",
                2024,
                "2024-01-01",
                "article",
                List.of("Author One", "Author Two"),
                "Test Journal",
                "Test Publisher",
                "en",
                List.of("Java", "Spring"),
                List.of("Software Engineering"),
                42,
                "https://example.com/source",
                LocalDateTime.of(2026, 6, 12, 12, 0)
        );;

        PublicationMetadata result = mapper.toPublicationMetadata(publication);

        assertEquals(publication.getOpenAlexId(), result.openAlexId());
        assertEquals(publication.getTitle(), result.title());
        assertEquals(publication.getAuthors(), result.authors());
        assertEquals(publication.getKeywords(), result.keywords());
        assertEquals(publication.getTopics(), result.topics());
        assertEquals(publication.getSourceUrl(), result.sourceUrl());
    }

    @Test
    void shouldHandleNullValues() {
        PublicationMetadata metadata = new PublicationMetadata(
                "id",
                null,
                "title",
                null,
                null,
                null,
                List.of(),
                null,
                null,
                null,
                List.of(),
                List.of(),
                null,
                null
        );

        ViewedPublication result = mapper.toViewedPublication(metadata);

        assertEquals("id", result.getOpenAlexId());
        assertNull(result.getDoi());
        assertNull(result.getPublicationYear());
        assertNull(result.getPublicationDate());
        assertNull(result.getType());
        assertNull(result.getJournalName());
        assertNull(result.getPublisher());
        assertNull(result.getLanguage());
        assertNull(result.getCitedByCount());
        assertNull(result.getSourceUrl());

        assertEquals(List.of(), result.getAuthors());
        assertEquals(List.of(), result.getKeywords());
        assertEquals(List.of(), result.getTopics());

        assertNotNull(result.getViewedAt());
    }

}