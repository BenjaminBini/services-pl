package com.sully.covid.dal.service;

import com.sully.covid.dal.model.CentreCT;
import com.sully.covid.dal.repository.CentreCTRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class CentreCTService extends ServiceBase<CentreCTRepository, CentreCT> {

    @Autowired
    public CentreCTService(CentreCTRepository repository) {
        this.repository = repository;
    }

    @Override
    public Page<CentreCT> search(Pageable pageable, String keyword, String filter) {
        return this.repository.findByNomContaining(keyword, pageable);
    }

}