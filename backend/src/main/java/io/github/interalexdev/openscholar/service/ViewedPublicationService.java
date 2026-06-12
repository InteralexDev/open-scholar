package io.github.interalexdev.openscholar.service;

import io.github.interalexdev.openscholar.entity.ViewedPublication;
import io.github.interalexdev.openscholar.mapper.ViewedPublicationMapper;
import io.github.interalexdev.openscholar.model.PublicationMetadata;
import io.github.interalexdev.openscholar.repository.ViewedPublicationRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ViewedPublicationService {

    private final ViewedPublicationRepository viewedPublicationRepository;
    private final ViewedPublicationMapper viewedPublicationMapper;

    public ViewedPublicationService(
            ViewedPublicationRepository viewedPublicationRepository,
            ViewedPublicationMapper viewedPublicationMapper
    ) {
        this.viewedPublicationRepository = viewedPublicationRepository;
        this.viewedPublicationMapper = viewedPublicationMapper;
    }

    public void saveOrUpdate(PublicationMetadata metadata) {
        viewedPublicationRepository.findByOpenAlexId(metadata.openAlexId())
                .ifPresentOrElse(
                        publication -> updateViewedAt(publication),
                        () -> viewedPublicationRepository.save(
                                viewedPublicationMapper.toViewedPublication(metadata)
                        )
                );
    }

    private void updateViewedAt(ViewedPublication publication) {
        publication.setViewedAt(LocalDateTime.now());
        viewedPublicationRepository.save(publication);
    }

    @Transactional(readOnly = true)
    public List<PublicationMetadata> getRecentlyViewedPublications() {
        return viewedPublicationRepository.findTop10ByOrderByViewedAtDesc()
                .stream()
                .map(viewedPublicationMapper::toPublicationMetadata)
                .toList();
    }

}