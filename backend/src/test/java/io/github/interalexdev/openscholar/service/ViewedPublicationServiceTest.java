package io.github.interalexdev.openscholar.service;

import io.github.interalexdev.openscholar.entity.ViewedPublication;
import io.github.interalexdev.openscholar.mapper.ViewedPublicationMapper;
import io.github.interalexdev.openscholar.model.PublicationMetadata;
import io.github.interalexdev.openscholar.repository.ViewedPublicationRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ViewedPublicationServiceTest {

    @Mock
    private ViewedPublicationRepository viewedPublicationRepository;

    @Mock
    private ViewedPublicationMapper viewedPublicationMapper;

    @InjectMocks
    private ViewedPublicationService viewedPublicationService;

    @Test
    void saveOrUpdate_shouldSaveNewPublicationWhenNotAlreadyViewed() {

        PublicationMetadata metadata = new PublicationMetadata(
                "id",
                null,
                "title",
                2024,
                "2024-01-01",
                "article",
                List.of(),
                null,
                null,
                "en",
                List.of(),
                List.of(),
                10,
                null
        );

        ViewedPublication publication = new ViewedPublication();

        when(viewedPublicationRepository.findByOpenAlexId("id"))
                .thenReturn(Optional.empty());

        when(viewedPublicationMapper.toViewedPublication(metadata))
                .thenReturn(publication);

        when(viewedPublicationRepository.findAllByOrderByViewedAtDesc())
                .thenReturn(List.of(publication));

        viewedPublicationService.saveOrUpdate(metadata);

        verify(viewedPublicationMapper).toViewedPublication(metadata);
        verify(viewedPublicationRepository).save(publication);
    }

    @Test
    void saveOrUpdate_shouldUpdateViewedAtWhenPublicationAlreadyExists() {

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

        ViewedPublication publication = new ViewedPublication();
        publication.setViewedAt(LocalDateTime.now().minusDays(1));

        when(viewedPublicationRepository.findByOpenAlexId("id"))
                .thenReturn(Optional.of(publication));

        when(viewedPublicationRepository.findAllByOrderByViewedAtDesc())
                .thenReturn(List.of(publication));

        viewedPublicationService.saveOrUpdate(metadata);

        verify(viewedPublicationRepository).save(publication);
        verify(viewedPublicationMapper, never()).toViewedPublication(any());
    }

    @Test
    void saveOrUpdate_shouldDeleteOldPublicationsWhenHistoryExceeds10() {

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

        ViewedPublication publication = new ViewedPublication();

        List<ViewedPublication> publications = new ArrayList<>();

        for (int i = 0; i < 11; i++) {
            publications.add(new ViewedPublication());
        }

        when(viewedPublicationRepository.findByOpenAlexId("id"))
                .thenReturn(Optional.empty());

        when(viewedPublicationMapper.toViewedPublication(metadata))
                .thenReturn(publication);

        when(viewedPublicationRepository.findAllByOrderByViewedAtDesc())
                .thenReturn(publications);

        viewedPublicationService.saveOrUpdate(metadata);

        verify(viewedPublicationRepository).deleteAll(anyList());
    }

    @Test
    void getRecentlyViewedPublications_shouldReturnMappedMetadata() {

        ViewedPublication publication1 = new ViewedPublication();
        ViewedPublication publication2 = new ViewedPublication();

        PublicationMetadata metadata1 = new PublicationMetadata(
                "id1",
                null,
                "title1",
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

        PublicationMetadata metadata2 = new PublicationMetadata(
                "id2",
                null,
                "title2",
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

        when(viewedPublicationRepository.findTop10ByOrderByViewedAtDesc())
                .thenReturn(List.of(publication1, publication2));

        when(viewedPublicationMapper.toPublicationMetadata(publication1))
                .thenReturn(metadata1);

        when(viewedPublicationMapper.toPublicationMetadata(publication2))
                .thenReturn(metadata2);

        List<PublicationMetadata> result =
                viewedPublicationService.getRecentlyViewedPublications();

        assertEquals(2, result.size());
        assertEquals("id1", result.get(0).openAlexId());
        assertEquals("id2", result.get(1).openAlexId());

        verify(viewedPublicationMapper, times(2))
                .toPublicationMetadata(any());
    }
}