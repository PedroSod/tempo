package com.ecore.tempo.exception;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.springframework.http.HttpStatus;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class ErrorResponse {

    private int    errorCode;
    private String error;
    private String message;

    public ErrorResponse(HttpStatus status, String message) {
        this.errorCode = status.value();
        this.error = status.name();
        this.message = message;
    }


}
