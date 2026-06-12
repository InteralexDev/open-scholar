package io.github.interalexdev.openscholar.controller;

import io.github.interalexdev.openscholar.dto.openalex.OpenAlexSearchResponse;
import io.github.interalexdev.openscholar.service.PublicationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/publications")
@Tag(name = "Publications", description = "Operations for searching scholarly publications")
public class PublicationController {

    private final PublicationService publicationService;

    public PublicationController(PublicationService publicationService) {
        this.publicationService = publicationService;
    }

    /**
     * Searches publications in OpenAlex.
     *
     * @param query user search query
     * @return up to 10 matching publications
     */
    @GetMapping("/search")
    @Operation(
            summary = "Search publications",
            description = "Searches scholarly publications using the OpenAlex API and returns up to 10 matching results."
    )
    public OpenAlexSearchResponse searchPublications(
            @RequestParam String query
    ) {
        return publicationService.searchPublications(query);
    }
}