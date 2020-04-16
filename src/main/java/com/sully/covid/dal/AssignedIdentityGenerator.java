package com.sully.covid.dal;

import com.sully.covid.dal.model.ModelBase;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentityGenerator;

import java.io.Serializable;

public class AssignedIdentityGenerator
        extends IdentityGenerator {

    @Override
    public Serializable generate(SharedSessionContractImplementor session,
                                 Object obj) {
        if(obj instanceof ModelBase) {
            ModelBase identifiable = (ModelBase) obj;
            long id =  identifiable.getId();
            if(id > 0) {
                return id;
            }
        }
        return super.generate(session, obj);
    }

}