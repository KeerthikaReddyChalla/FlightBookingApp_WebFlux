package com.flightapp.exception;

import com.flightapp.dto.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.support.WebExchangeBindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.*;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private ErrorResponse buildError(String msg, HttpStatus status, String path) {
        return ErrorResponse.builder()
                .message(msg)
                .status(status.value())
                .timestamp(LocalDateTime.now().toString())
                .path(path)
                .build();
    }


    @ExceptionHandler(WebExchangeBindException.class)
    public Mono<ErrorResponse> handleValidationErrors(WebExchangeBindException ex, ServerWebExchange exchange) {

        String errorMessage = ex.getFieldErrors().isEmpty() ?
                "Validation error" :
                ex.getFieldErrors().get(0).getDefaultMessage();

        return Mono.just(
                buildError(errorMessage, HttpStatus.BAD_REQUEST, exchange.getRequest().getPath().value())
        );
    }


    @ExceptionHandler(ResourceNotFoundException.class)
    public Mono<ErrorResponse> handleNotFound(ResourceNotFoundException ex, ServerWebExchange exchange) {
        return Mono.just(
                buildError(ex.getMessage(), HttpStatus.NOT_FOUND, exchange.getRequest().getPath().value())
        );
    }


    @ExceptionHandler(BadRequestException.class)
    public Mono<ErrorResponse> handleBadRequest(BadRequestException ex, ServerWebExchange exchange) {
        return Mono.just(
                buildError(ex.getMessage(), HttpStatus.BAD_REQUEST, exchange.getRequest().getPath().value())
        );
    }


    @ExceptionHandler(ResponseStatusException.class)
    public Mono<ErrorResponse> handleStatusException(ResponseStatusException ex, ServerWebExchange exchange) {
        HttpStatus status = ex.getStatusCode() instanceof HttpStatus httpStatus ? httpStatus : HttpStatus.BAD_REQUEST;

        return Mono.just(
                buildError(ex.getReason() != null ? ex.getReason() : "Error",
                        status,
                        exchange.getRequest().getPath().value())
        );
    }

  
    @ExceptionHandler(Exception.class)
    public Mono<ErrorResponse> handleOtherExceptions(Exception ex, ServerWebExchange exchange) {

        ex.printStackTrace(); 

        return Mono.just(
                buildError(ex.getMessage(),
                        HttpStatus.INTERNAL_SERVER_ERROR,
                        exchange.getRequest().getPath().value())
        );
    }
}
