package io.github.interalexdev.openscholar.service;

import io.github.interalexdev.openscholar.entity.ViewedPublication;
import io.github.interalexdev.openscholar.repository.ViewedPublicationRepository;
import org.springframework.stereotype.Service;

@Service
public class ViewedPublicationService {

    private final ViewedPublicationRepository viewedPublicationRepository;

    public ViewedPublicationService(
            ViewedPublicationRepository viewedPublicationRepository
    ) {
        this.viewedPublicationRepository = viewedPublicationRepository;
    }

    public ViewedPublication save(ViewedPublication publication) {
        return viewedPublicationRepository.save(publication);
    }
}