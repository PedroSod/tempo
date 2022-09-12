package com.ecore.tempo.exception;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Collection;

import org.springframework.http.HttpStatus;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class ErrorResponseWithFields {

    private int                errorCode;
    private String             error;
    private Collection<String> fieldErrors;

    public ErrorResponseWithFields(HttpStatus status, Collection<String> fieldErrors) {
        this.errorCode = status.value();
        this.error = status.name();
        this.fieldErrors = fieldErrors;
    }

}
