CREATE TABLE viewed_publications (
    id BIGSERIAL PRIMARY KEY,
    open_alex_id TEXT NOT NULL UNIQUE,
    doi TEXT,
    title TEXT NOT NULL,
    publication_year INTEGER,
    publication_date TEXT,
    type TEXT,
    journal_name TEXT,
    publisher TEXT,
    language TEXT,
    cited_by_count INTEGER,
    source_url TEXT,
    viewed_at TIMESTAMP NOT NULL
);

CREATE TABLE viewed_publication_authors (
    publication_id BIGINT NOT NULL,
    author TEXT NOT NULL,

    PRIMARY KEY (publication_id, author),

    FOREIGN KEY (publication_id)
        REFERENCES viewed_publications(id)
        ON DELETE CASCADE
);

CREATE TABLE viewed_publication_keywords (
    publication_id BIGINT NOT NULL,
    keyword TEXT NOT NULL,

    PRIMARY KEY (publication_id, keyword),

    FOREIGN KEY (publication_id)
        REFERENCES viewed_publications(id)
        ON DELETE CASCADE
);

CREATE TABLE viewed_publication_topics (
    publication_id BIGINT NOT NULL,
    topic TEXT NOT NULL,

    PRIMARY KEY (publication_id, topic),

    FOREIGN KEY (publication_id)
        REFERENCES viewed_publications(id)
        ON DELETE CASCADE
);