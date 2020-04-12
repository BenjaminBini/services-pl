package com.sully.covid.dal.service;

import com.sully.covid.dal.model.CentreRoutier;
import com.sully.covid.dal.repository.CentreRoutierRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class CentreRoutierService extends ServiceBase<CentreRoutierRepository, CentreRoutier> {

    @Autowired
    public CentreRoutierService(CentreRoutierRepository repository) {
        this.repository = repository;
    }

    @Override
    public Page<CentreRoutier> search(Pageable pageable, String keyword, String filter) {
        return this.repository.findByNomCentreContaining(keyword, pageable);
    }
}