package com.sully.covid.dal.repository;

import com.sully.covid.dal.model.CentreRoutier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CentreRoutierRepository extends JpaRepository<CentreRoutier, Long> {
    Page<CentreRoutier> findByNomCentreContaining(String nomCentre, Pageable pageable);
}
