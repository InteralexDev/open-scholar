package io.github.interalexdev.openscholar.controller;

import io.github.interalexdev.openscholar.model.PublicationMetadata;
import io.github.interalexdev.openscholar.service.PublicationService;
import io.github.interalexdev.openscholar.service.ViewedPublicationService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(PublicationController.class)
class PublicationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private PublicationService publicationService;

    @MockitoBean
    private ViewedPublicationService viewedPublicationService;

    @Test
    void searchPublications_shouldReturnJson() throws Exception {

        PublicationMetadata metadata = new PublicationMetadata(
                "https://openalex.org/W123",
                "https://doi.org/10.123/example",
                "Test publication",
                2024,
                "2024-01-01",
                "article",
                List.of("Author One"),
                "Test Journal",
                "Test Publisher",
                "en",
                List.of("Java"),
                List.of("Software Engineering"),
                42,
                "https://example.com/source"
        );

        when(publicationService.searchPublications("spring"))
                .thenReturn(List.of(metadata));

        mockMvc.perform(get("/api/publications/search")
                        .param("query", "spring"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].title").value("Test publication"));
    }

    @Test
    void getRecentlyViewedPublications_shouldReturnJson() throws Exception {

        when(viewedPublicationService.getRecentlyViewedPublications())
                .thenReturn(List.of());

        mockMvc.perform(get("/api/publications/viewed"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }

    @Test
    void getPublicationDetails_shouldReturnJson() throws Exception {

        PublicationMetadata metadata = new PublicationMetadata(
                "id",
                null,
                "Test publication",
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

        when(publicationService.getPublicationDetails("W123"))
                .thenReturn(metadata);

        mockMvc.perform(get("/api/publications/W123"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.title").value("Test publication"));
    }

    @Test
    void exportPublicationJson_shouldReturnJson() throws Exception {

        PublicationMetadata metadata = new PublicationMetadata(
                "id",
                null,
                "Test publication",
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

        when(publicationService.exportPublicationJson("W123"))
                .thenReturn(metadata);

        mockMvc.perform(get("/api/publications/W123/export/json"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.title").value("Test publication"));
    }

    @Test
    void exportPublicationXml_shouldReturnXml() throws Exception {

        when(publicationService.exportPublicationDublinCore("W123"))
                .thenReturn("<xml>test</xml>");

        mockMvc.perform(get("/api/publications/W123/export/dublin-core"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_XML))
                .andExpect(content().string("<xml>test</xml>"));
    }
}