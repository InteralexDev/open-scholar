package io.github.interalexdev.openscholar.repository;

import io.github.interalexdev.openscholar.entity.ViewedPublication;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ViewedPublicationRepository extends JpaRepository<ViewedPublication, Long> {

    Optional<ViewedPublication> findByOpenAlexId(String openAlexId);

    List<ViewedPublication> findTop10ByOrderByViewedAtDesc();
}