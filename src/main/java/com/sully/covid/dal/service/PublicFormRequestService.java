package com.sully.covid.dal.service;

import com.sully.covid.dal.model.PublicFormRequest;
import com.sully.covid.dal.repository.PublicFormRequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PublicFormRequestService extends ServiceBase<PublicFormRequestRepository, PublicFormRequest> {

    @Autowired
    public PublicFormRequestService(PublicFormRequestRepository publicFormRequestRepository) {
        this.repository = publicFormRequestRepository;
    }
}
