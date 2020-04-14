package com.sully.covid.util;

import com.opencsv.bean.AbstractBeanField;
import com.opencsv.exceptions.CsvConstraintViolationException;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class StringToBoolConverter<T, I> extends AbstractBeanField<T, I> {

    @Override
    protected Object convert(String s) throws CsvDataTypeMismatchException, CsvConstraintViolationException {
        if (s.startsWith("Oui")) {
            return true;
        } else if (s.startsWith("Non")){
            return false;
        } else {
            return null;
        }
    }

    protected String convertToWrite(Object value) throws CsvDataTypeMismatchException, CsvRequiredFieldEmptyException {
        if (value == null) {
            return "-";
        } else if ((Boolean) value) {
            return "Oui";
        } else {
            return "Non";
        }
    }
}
