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
            // Placeholder so the RED tests compile; GREEN maps these to 409
            case CHARGE_POINT_OFFLINE, CHARGE_POINT_INOPERATIVE -> HttpStatus.INTERNAL_SERVER_ERROR;
        };
        var problem = ProblemDetail.forStatusAndDetail(status, e.code().name());
        problem.setProperty("code", e.code().name());
        return problem;
    }
}
