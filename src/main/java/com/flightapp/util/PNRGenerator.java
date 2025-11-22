package com.flightapp.util;

import java.util.UUID;

public class PNRGenerator {

    private PNRGenerator() {}

    public static String generatePNR() {
        return UUID.randomUUID()
                .toString()
                .substring(0, 8)
                .toUpperCase();
    }
}
