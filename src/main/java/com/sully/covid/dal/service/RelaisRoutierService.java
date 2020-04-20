package com.sully.covid.dal.service;

import com.sully.covid.dal.model.RelaisRoutier;
import com.sully.covid.dal.repository.RelaisRoutierRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RelaisRoutierService extends ServiceBase<RelaisRoutierRepository, RelaisRoutier> {

    @Autowired
    public RelaisRoutierService(RelaisRoutierRepository repository) {
        this.repository = repository;
    }

    @Override
    public Page<RelaisRoutier> search(Pageable pageable, String keyword, List<String> filter) {
        return this.repository.findByNomContaining(keyword, pageable);
    }
}
