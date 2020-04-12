package com.sully.covid.dal.repository;

import com.sully.covid.dal.model.Aire;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AireRepository extends JpaRepository<Aire, Long> {
    Page<Aire> findByNomAireContaining(String nomAire, Pageable pageable);

    Page<Aire> findByNomAireContainingAndDirSca(String nomAire, String dirSca, Pageable pageable);
}
