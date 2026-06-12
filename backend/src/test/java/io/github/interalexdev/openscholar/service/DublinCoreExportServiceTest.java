package io.github.interalexdev.openscholar.service;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import io.github.interalexdev.openscholar.mapper.DublinCoreMapper;
import io.github.interalexdev.openscholar.model.PublicationMetadata;
import io.github.interalexdev.openscholar.model.export.DublinCoreDocument;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DublinCoreExportServiceTest {

    @Mock
    private DublinCoreMapper dublinCoreMapper;

    private final XmlMapper xmlMapper = new XmlMapper();

    @Test
    void export_shouldReturnDublinCoreXml() {
        DublinCoreExportService dublinCoreExportService =
                new DublinCoreExportService(dublinCoreMapper, xmlMapper);

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

        DublinCoreDocument document = new DublinCoreDocument(
                "http://www.openarchives.org/OAI/2.0/oai_dc/",
                "http://purl.org/dc/elements/1.1/",
                "http://www.w3.org/2001/XMLSchema-instance",
                "http://www.openarchives.org/OAI/2.0/oai_dc/ http://www.openarchives.org/OAI/2.0/oai_dc.xsd",
                "Test publication",
                List.of("Author One", "Author Two"),
                "Test Publisher",
                "2024-01-01",
                "article",
                "en",
                "https://doi.org/10.123/example",
                List.of("Java", "Spring"),
                "https://example.com/source"
        );

        when(dublinCoreMapper.toDublinCoreDocument(metadata))
                .thenReturn(document);

        String xml = dublinCoreExportService.export(metadata);

        verify(dublinCoreMapper).toDublinCoreDocument(metadata);

        assertTrue(xml.contains("<dc:title>Test publication</dc:title>"));
        assertTrue(xml.contains("<dc:creator>Author One</dc:creator>"));
        assertTrue(xml.contains("<dc:creator>Author Two</dc:creator>"));
        assertTrue(xml.contains("<dc:publisher>Test Publisher</dc:publisher>"));
        assertTrue(xml.contains("<dc:identifier>https://doi.org/10.123/example</dc:identifier>"));
        assertTrue(xml.contains("<dc:subject>Java</dc:subject>"));
    }
}