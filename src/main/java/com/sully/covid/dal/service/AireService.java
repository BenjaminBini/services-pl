package com.sully.covid.dal.service;

import com.sully.covid.dal.model.Aire;
import com.sully.covid.dal.repository.AireRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AireService extends ServiceBase<AireRepository, Aire> {

    @Autowired
    public AireService(AireRepository repository) {
        this.repository = repository;
    }

    @Override
    public Page<Aire> search(Pageable pageable, String keyword, List<String> filter) {
        Page<Aire> aires;
        Sort sort;
        String sortProperty = pageable.getSort().get().findFirst().get().getProperty();
        String sortColumn;
        switch (sortProperty) {
            case "nomAire":
                sortColumn = "NOM_AIRE";
                break;
            case "typeAire":
                sortColumn = "TYPE_AIRE";
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
        if (filter == null || filter.equals("")) {
            aires = this.repository.search(keyword, PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), sort));
        } else {
            aires = this.repository.search(keyword, filter, PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), sort));
        }
        return aires;
    }

    public List<String> getRoutes(String dep) {
        return this.getAll().stream()
                .filter(a -> dep == null || a.getDep().equals(dep))
                .map(Aire::getRoute)
                .distinct()
                .sorted()
                .collect(Collectors.toList());
    }

    public List<String> getDepartements() {
        return this.getAll().stream()
                .map(Aire::getDep)
                .distinct()
                .sorted()
                .collect(Collectors.toList());
    }
}
