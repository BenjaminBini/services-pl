package com.sully.covid.dal.repository;

import com.sully.covid.dal.model.RelaisRoutier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RelaisRoutierRepository extends JpaRepository<RelaisRoutier, Long> {
    Page<RelaisRoutier> findByNomContaining(String nom, Pageable pageable);
}
