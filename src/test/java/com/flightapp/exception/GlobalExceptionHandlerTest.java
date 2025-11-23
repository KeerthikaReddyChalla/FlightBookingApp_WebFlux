package com.flightapp.exception;

import com.flightapp.dto.ErrorResponse;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.http.HttpStatus;
import org.springframework.mock.web.server.MockServerWebExchange;
import org.springframework.mock.http.server.reactive.MockServerHttpRequest;

import org.springframework.web.bind.support.WebExchangeBindException;
import org.springframework.validation.BeanPropertyBindingResult;

import reactor.core.publisher.Mono;

import static org.junit.jupiter.api.Assertions.*;

class GlobalExceptionHandlerTest {

    private GlobalExceptionHandler handler;
    private MockServerWebExchange exchange;

    @BeforeEach
    void setup() {
        handler = new GlobalExceptionHandler();
        exchange = MockServerWebExchange.from(
                MockServerHttpRequest.get("/test").build()
        );
    }

    @Test
    void testResourceNotFound() {

        Mono<ErrorResponse> respMono =
                handler.handleNotFound(
                        new ResourceNotFoundException("Not found"),
                        exchange
                );

        ErrorResponse resp = respMono.block();

        assertEquals("Not found", resp.getMessage());
        assertEquals(404, resp.getStatus());
    }

    @Test
    void testBadRequest() {

        Mono<ErrorResponse> respMono =
                handler.handleBadRequest(
                        new BadRequestException("Invalid"),
                        exchange
                );

        ErrorResponse resp = respMono.block();

        assertEquals("Invalid", resp.getMessage());
        assertEquals(400, resp.getStatus());
    }

    @Test
    void testValidationError() {

        class Dummy {
            private String field;
            public String getField() { return field; }
            public void setField(String field) { this.field = field; }
        }

        Dummy target = new Dummy();
        var errors = new BeanPropertyBindingResult(target, "dummy");

        errors.rejectValue("field", "invalid", "Field is invalid");

        WebExchangeBindException ex = new WebExchangeBindException(null, errors);

        Mono<ErrorResponse> mono = handler.handleValidationErrors(ex, exchange);

        ErrorResponse resp = mono.block();

        assertEquals("Field is invalid", resp.getMessage());
        assertEquals(400, resp.getStatus());
    }


    @Test
    void testGenericException() {

        Mono<ErrorResponse> respMono =
                handler.handleOtherExceptions(
                        new RuntimeException("Boom"),
                        exchange
                );

        ErrorResponse resp = respMono.block();

        assertEquals("Boom", resp.getMessage());
        assertEquals(500, resp.getStatus());
    }

    @Test
    void testResponseStatusException() {

        var ex = new org.springframework.web.server.ResponseStatusException(
                HttpStatus.NOT_FOUND,
                "Missing"
        );

        Mono<ErrorResponse> respMono =
                handler.handleStatusException(ex, exchange);

        ErrorResponse resp = respMono.block();

        assertEquals("Missing", resp.getMessage());
        assertEquals(404, resp.getStatus());
    }
}
