package com.sully.covid.dal.repository;

import com.sully.covid.dal.model.RelaisRoutier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface RelaisRoutierRepository extends JpaRepository<RelaisRoutier, Long> {
    @Query(value = "SELECT count(p.ID) as requestsCount, r.* FROM RELAIS_ROUTIER r " +
            "LEFT JOIN PUBLIC_FORM_REQUEST p ON p.ID_RELAIS = r.ID " +
            "WHERE (r.NOM like %?1%) " +
            "GROUP BY r.id",
            countQuery = "SELECT COUNT(*) FROM RELAIS_ROUTIER r " +
                    "LEFT JOIN PUBLIC_FORM_REQUEST p ON p.ID_RELAIS = r.ID " +
                    "WHERE r.NOM like %?1% " +
                    "GROUP BY r.id",
            nativeQuery = true)
    Page<RelaisRoutier> search(String keyword, Pageable pageable);

}
