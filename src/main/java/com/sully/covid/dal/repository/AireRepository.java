package com.sully.covid.dal.repository;

import com.sully.covid.dal.model.Aire;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface AireRepository extends JpaRepository<Aire, Long> {

    @Query(value = "SELECT count(p.ID) as requestsCount, a.* FROM aires a " +
            "LEFT JOIN PUBLIC_FORM_REQUEST p ON p.ID_AIRE = a.ID " +
            "WHERE a.NOM_AIRE like %?1% AND DIR_SCA = ?2 " +
            "GROUP BY a.id",
            countQuery = "SELECT COUNT(*) FROM aires a " +
                    "LEFT JOIN PUBLIC_FORM_REQUEST p ON p.ID_AIRE = a.ID " +
                    "WHERE a.NOM_AIRE like %?1% AND DIR_SCA = ?2 " +
                    "GROUP BY a.id",
            nativeQuery = true)
    Page<Aire> search(String nomAire, String dirSca, Pageable pageable);


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

}
