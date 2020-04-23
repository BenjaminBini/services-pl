package com.sully.covid.dal.repository;

import com.sully.covid.dal.model.CentreCT;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CentreCTRepository extends JpaRepository<CentreCT, Long> {
    @Query(value = "SELECT count(p.ID) as requestsCount, c.* FROM CENTRE_CT c " +
            "LEFT JOIN PUBLIC_FORM_REQUEST p ON p.ID_CT = c.ID " +
            "WHERE (c.NOM like %?1% or c.COMMUNE like %?2%) " +
            "GROUP BY c.id",
            countQuery = "SELECT COUNT(*) FROM CENTRE_CT c " +
                    "LEFT JOIN PUBLIC_FORM_REQUEST p ON p.ID_CT = c.ID " +
                    "WHERE (c.NOM like %?1% or c.COMMUNE like %?2%) " +
                    "GROUP BY c.id",
            nativeQuery = true)
    Page<CentreCT> search(String nom, String commune, Pageable pageable);

    CentreCT findByCodeAgrem(String codeAgrem);
}
