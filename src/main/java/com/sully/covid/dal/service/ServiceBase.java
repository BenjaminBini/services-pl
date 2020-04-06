package com.sully.covid.dal.service;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public class ServiceBase<T extends JpaRepository<U, Long>, U> {
    protected T repository;


    public Page<U> getAll(Pageable page) {
        return this.repository.findAll(page);
    }

    public List<U> getAll() {
        return this.repository.findAll(Sort.by(Sort.Direction.DESC, "id"));
    }

    public U save(U entity) {
        return this.repository.save(entity);
    }

    public U get(long id) {
        return this.repository.getOne(id);
    }

    public void delete(long id) {
        this.repository.deleteById(id);
    }

}
