package com.flightapp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.reactive.server.WebTestClient;

public abstract class BaseTestConfig {

    @Autowired
    protected WebTestClient webTestClient;
}
