package com.sully.covid.dal.repository;

import com.sully.covid.dal.model.Aire;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AireRepository extends JpaRepository<Aire, Long> {

    @Query(value = "SELECT count(p.ID), a.* FROM aires a WHERE nomAire like '%%' and dirSca = ?2" +
            "LEFT JOIN PUBLIC_FORM_REQUEST p ON p.ID_AIRE = a.ID" +
            "GROUP BY a.id" +
            "ORDER BY count(p.ID) desc",
            nativeQuery = true)
    List<Aire> search(String nomAire, String dirSca, String sort, String direction);


    @Query(value = "SELECT count(p.ID) as requestsCount, a.* FROM aires a " +
            "LEFT JOIN PUBLIC_FORM_REQUEST p ON p.ID_AIRE = a.ID " +
            "WHERE a.NOM_AIRE like %?1% " +
            "GROUP BY a.id",
            countQuery = "SELECT COUNT(*) FROM aires a " +
                    "LEFT JOIN PUBLIC_FORM_REQUEST p ON p.ID_AIRE = a.ID " +
                    "WHERE a.NOM_AIRE like %?1% " +
                    "GROUP BY a.id",
            nativeQuery = true)
    Page<Aire> search(String nomAire, Pageable pageable);

    Page<Aire> findByNomAireContaining(String nomAire, Pageable pageable);

    Page<Aire> findByNomAireContainingAndDirSca(String nomAire, String dirSca, Pageable pageable);
}
