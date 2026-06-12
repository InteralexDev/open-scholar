package io.github.interalexdev.openscholar.repository;

import io.github.interalexdev.openscholar.entity.ViewedPublication;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ViewedPublicationRepository
        extends JpaRepository<ViewedPublication, Long> {
}