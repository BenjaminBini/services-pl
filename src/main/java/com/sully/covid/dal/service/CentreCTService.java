package com.sully.covid.dal.service;

import com.sully.covid.dal.model.CentreCT;
import com.sully.covid.dal.repository.CentreCTRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CentreCTService extends ServiceBase<CentreCTRepository, CentreCT> {

    @Autowired
    public CentreCTService(CentreCTRepository repository) {
        this.repository = repository;
    }

    @Override
    public Page<CentreCT> search(Pageable pageable, String keyword, List<String> filter) {
        Page<CentreCT> cts;
        Sort sort;
        String sortProperty = pageable.getSort().get().findFirst().get().getProperty();
        String sortColumn;
        switch (sortProperty) {
            case "nom":
                sortColumn = "NOM";
                break;
            case "statutOuvert":
                sortColumn = "STATUT_OUVERT";
                break;
            default:
                sortColumn = "ID";
        }
        if (pageable.getSort().get().findFirst().get().getDirection() == Sort.Direction.DESC) {
            sort = Sort.by(Sort.Order.desc("requestsCount"), Sort.Order.desc(sortColumn));
        } else {
            sort = Sort.by(Sort.Order.desc("requestsCount"), Sort.Order.asc(sortColumn));
        }
        cts = this.repository.search(keyword, keyword, PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), sort));
        return cts;
    }

    public CentreCT findByCodeAgrem(String codeAgrem) {
        return this.repository.findByCodeAgrem(codeAgrem);
    }

    public List<String> getDepartements() {
        return this.getAll().stream()
                .map(CentreCT::getDep)
                .distinct()
                .sorted()
                .collect(Collectors.toList());
    }
}