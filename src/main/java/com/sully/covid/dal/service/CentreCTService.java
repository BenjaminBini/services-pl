package com.sully.covid.dal.service;

import com.sully.covid.dal.model.CentreCT;
import com.sully.covid.dal.repository.CentreCTRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class CentreCTService extends ServiceBase<CentreCTRepository, CentreCT> {

    @Autowired
    public CentreCTService(CentreCTRepository repository) {
        this.repository = repository;
    }

}