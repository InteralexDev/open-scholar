package io.github.interalexdev.openscholar.service;

import io.github.interalexdev.openscholar.client.OpenAlexClient;
import io.github.interalexdev.openscholar.dto.openalex.OpenAlexSearchResponse;
import io.github.interalexdev.openscholar.dto.openalex.OpenAlexWorkDto;
import io.github.interalexdev.openscholar.mapper.OpenAlexMapper;
import io.github.interalexdev.openscholar.model.PublicationMetadata;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PublicationServiceTest {

    @Mock
    private OpenAlexClient openAlexClient;

    @Mock
    private OpenAlexMapper openAlexMapper;

    @Mock
    private ViewedPublicationService viewedPublicationService;

    @Mock
    private DublinCoreExportService dublinCoreExportService;

    @InjectMocks
    private PublicationService publicationService;

    @Test
    void searchPublications_shouldReturnMappedPublications() {
        OpenAlexWorkDto work = new OpenAlexWorkDto(
                "https://openalex.org/W123",
                "https://doi.org/10.123/example",
                null,
                "Test publication",
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

        OpenAlexSearchResponse response = new OpenAlexSearchResponse(
                List.of(work)
        );

        PublicationMetadata metadata = new PublicationMetadata(
                "https://openalex.org/W123",
                "https://doi.org/10.123/example",
                "Test publication",
                2024,
                "2024-01-01",
                "article",
                List.of(),
                null,
                null,
                "en",
                List.of(),
                List.of(),
                42,
                null
        );

        when(openAlexClient.searchWorks("spring boot")).thenReturn(response);
        when(openAlexMapper.toPublicationMetadata(work)).thenReturn(metadata);

        List<PublicationMetadata> result =
                publicationService.searchPublications("spring boot");

        assertEquals(1, result.size());
        assertEquals("Test publication", result.getFirst().title());

        verify(openAlexClient).searchWorks("spring boot");
        verify(openAlexMapper).toPublicationMetadata(work);
    }

    @Test
    void getPublicationDetails_shouldSaveViewedPublication() {
        OpenAlexWorkDto work = new OpenAlexWorkDto(
                "https://openalex.org/W123",
                "https://doi.org/10.123/example",
                null,
                "Test publication",
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

        PublicationMetadata metadata = new PublicationMetadata(
                "https://openalex.org/W123",
                "https://doi.org/10.123/example",
                "Test publication",
                2024,
                "2024-01-01",
                "article",
                List.of(),
                null,
                null,
                "en",
                List.of(),
                List.of(),
                42,
                null
        );

        when(openAlexClient.getPublicationDetails("W123")).thenReturn(work);
        when(openAlexMapper.toPublicationMetadata(work)).thenReturn(metadata);

        PublicationMetadata result = publicationService.getPublicationDetails("W123");

        assertEquals("Test publication", result.title());

        verify(openAlexClient).getPublicationDetails("W123");
        verify(openAlexMapper).toPublicationMetadata(work);
        verify(viewedPublicationService).saveOrUpdate(metadata);
    }

    @Test
    void exportPublicationJson_shouldNotSaveViewedPublication() {
        OpenAlexWorkDto work = new OpenAlexWorkDto(
                "https://openalex.org/W123",
                "https://doi.org/10.123/example",
                null,
                "Test publication",
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

        PublicationMetadata metadata = new PublicationMetadata(
                "https://openalex.org/W123",
                "https://doi.org/10.123/example",
                "Test publication",
                2024,
                "2024-01-01",
                "article",
                List.of(),
                null,
                null,
                "en",
                List.of(),
                List.of(),
                42,
                null
        );

        when(openAlexClient.getPublicationDetails("W123")).thenReturn(work);
        when(openAlexMapper.toPublicationMetadata(work)).thenReturn(metadata);

        PublicationMetadata result = publicationService.exportPublicationJson("W123");

        assertEquals("Test publication", result.title());

        verify(openAlexClient).getPublicationDetails("W123");
        verify(openAlexMapper).toPublicationMetadata(work);
        verify(viewedPublicationService, never()).saveOrUpdate(any());
    }

    @Test
    void exportPublicationDublinCore_shouldReturnXmlWithoutSavingViewedPublication() {
        OpenAlexWorkDto work = new OpenAlexWorkDto(
                "https://openalex.org/W123",
                "https://doi.org/10.123/example",
                null,
                "Test publication",
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

        PublicationMetadata metadata = new PublicationMetadata(
                "https://openalex.org/W123",
                "https://doi.org/10.123/example",
                "Test publication",
                2024,
                "2024-01-01",
                "article",
                List.of(),
                null,
                null,
                "en",
                List.of(),
                List.of(),
                42,
                null
        );

        when(openAlexClient.getPublicationDetails("W123")).thenReturn(work);
        when(openAlexMapper.toPublicationMetadata(work)).thenReturn(metadata);
        when(dublinCoreExportService.export(metadata)).thenReturn("<oai_dc:dc></oai_dc:dc>");

        String result = publicationService.exportPublicationDublinCore("W123");

        assertEquals("<oai_dc:dc></oai_dc:dc>", result);

        verify(openAlexClient).getPublicationDetails("W123");
        verify(openAlexMapper).toPublicationMetadata(work);
        verify(dublinCoreExportService).export(metadata);
        verify(viewedPublicationService, never()).saveOrUpdate(any());
    }
}