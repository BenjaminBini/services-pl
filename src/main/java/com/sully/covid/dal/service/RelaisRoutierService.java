package com.sully.covid.dal.service;

import com.sully.covid.dal.model.RelaisRoutier;
import com.sully.covid.dal.repository.RelaisRoutierRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class RelaisRoutierService extends ServiceBase<RelaisRoutierRepository, RelaisRoutier> {

    @Autowired
    public RelaisRoutierService(RelaisRoutierRepository repository) {
        this.repository = repository;
    }

}
