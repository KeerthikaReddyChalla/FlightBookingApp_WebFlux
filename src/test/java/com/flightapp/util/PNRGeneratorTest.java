package com.flightapp.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PNRGeneratorTest {

    @Test
    void testGeneratePNR() {

        String pnr = PNRGenerator.generatePNR();

        assertNotNull(pnr);
        assertEquals(8, pnr.length());
        assertTrue(pnr.matches("[A-Z0-9]{8}"));
    }

    @Test
    void testPNRIsUnique() {

        String p1 = PNRGenerator.generatePNR();
        String p2 = PNRGenerator.generatePNR();

        assertNotEquals(p1, p2);
    }
}
