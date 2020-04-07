package com.sully.covid.dal.repository;

import com.sully.covid.dal.model.CentreCT;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CentreCTRepository extends JpaRepository<CentreCT, Long> {
    Page<CentreCT> findByNomContaining(String nom, Pageable pageable);
}
