package com.sully.covid.dal.service;

import com.sully.covid.dal.model.CentreRoutier;
import com.sully.covid.dal.repository.CentreRoutierRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class CentreRoutierService extends ServiceBase<CentreRoutierRepository, CentreRoutier> {

    @Autowired
    public CentreRoutierService(CentreRoutierRepository repository) {
        this.repository = repository;
    }

}