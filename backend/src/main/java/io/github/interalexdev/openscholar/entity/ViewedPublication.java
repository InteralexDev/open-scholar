package io.github.interalexdev.openscholar.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "viewed_publications")
@Getter
@Setter
public class ViewedPublication {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String openAlexId;

    private String doi;

    private String title;

    private Integer publicationYear;

    private String publicationDate;

    private String type;

    @ElementCollection
    @CollectionTable(
            name = "viewed_publication_authors",
            joinColumns = @JoinColumn(name = "publication_id")
    )
    @Column(name = "author")
    private List<String> authors;

    private String journalName;

    private String publisher;

    private String language;

    @ElementCollection
    @CollectionTable(
            name = "viewed_publication_keywords",
            joinColumns = @JoinColumn(name = "publication_id")
    )
    @Column(name = "keyword")
    private List<String> keywords;

    @ElementCollection
    @CollectionTable(
            name = "viewed_publication_topics",
            joinColumns = @JoinColumn(name = "publication_id")
    )
    @Column(name = "topic")
    private List<String> topics;

    private Integer citedByCount;

    private String sourceUrl;

    private LocalDateTime viewedAt;

    public ViewedPublication() {
    }

}