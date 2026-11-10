package com.example.chargeops.api;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/** Single place that maps errors to HTTP. Every REST error goes through here. */
@RestControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(ApiException.class)
    public ProblemDetail handle(ApiException e) {
        var status = switch (e.code()) {
            case CHARGE_POINT_NOT_FOUND -> HttpStatus.NOT_FOUND;
        };
        var problem = ProblemDetail.forStatusAndDetail(status, e.code().name());
        problem.setProperty("code", e.code().name());
        return problem;
    }
}
