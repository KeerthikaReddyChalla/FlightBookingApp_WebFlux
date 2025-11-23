package com.flightapp;

import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.test.web.reactive.server.WebTestClient;

@WebFluxTest
public abstract class BaseTestConfig {

    @Autowired
    protected WebTestClient webTestClient;

    @BeforeEach
    void setup() {
        
    }
}
