package com.ecore.tempo.helper;

import java.util.UUID;

import org.springframework.data.domain.ExampleMatcher;

public class ServiceHelper {
    private ServiceHelper(){}
    public static String generateUUID() {
        return UUID.randomUUID().toString();
    }

    public ExampleMatcher getFullScanExample() {
        return ExampleMatcher.matching()
                             .withIgnorePaths("id")
                             .withIgnoreNullValues();
    }


}
