package com.sully.covid.util;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Entry {
    private final String key;
    private final String value;
}
