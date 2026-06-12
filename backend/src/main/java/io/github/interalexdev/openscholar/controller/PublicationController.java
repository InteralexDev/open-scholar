package io.github.interalexdev.openscholar.controller;

import io.github.interalexdev.openscholar.model.PublicationMetadata;
import io.github.interalexdev.openscholar.service.PublicationService;
import io.github.interalexdev.openscholar.service.ViewedPublicationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/publications")
@Tag(name = "Publications", description = "Operations for searching scholarly publications")
public class PublicationController {

    private final PublicationService publicationService;
    private final ViewedPublicationService viewedPublicationService;

    public PublicationController(PublicationService publicationService, ViewedPublicationService viewedPublicationService) {
        this.publicationService = publicationService;
        this.viewedPublicationService = viewedPublicationService;
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
    public List<PublicationMetadata> searchPublications(
            @RequestParam String query
    ) {
        return publicationService.searchPublications(query);
    }

    @Operation(
            summary = "Get recently viewed publications",
            description = "Returns the 10 most recently viewed publications stored locally in the database."
    )
    @GetMapping("/viewed")
    public List<PublicationMetadata> getRecentlyViewedPublications() {
        System.out.println("VIEWED ENDPOINT CALLED");
        return viewedPublicationService.getRecentlyViewedPublications();
    }

    @GetMapping("/{openAlexId}")
    @Operation(
            summary = "Get publication details",
            description = "Retrieves the details of a publication from OpenAlex."
    )
    public PublicationMetadata getPublicationDetails(
            @PathVariable String openAlexId
    ) {
        return publicationService.getPublicationDetails(openAlexId);
    }

    @GetMapping("/{openAlexId}/export/json")
    @Operation(
            summary = "Export publication metadata as JSON",
            description = "Retrieves publication metadata from OpenAlex and returns it as JSON without saving it to the local history."
    )
    public PublicationMetadata exportPublicationJson(
            @PathVariable String openAlexId
    ) {
        return publicationService.exportPublicationJson(openAlexId);
    }

}