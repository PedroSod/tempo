package com.ecore.tempo.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException.BadRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@EnableWebMvc
@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status,
                                                                  WebRequest request) {
        return new ResponseEntity<>(new ErrorResponseWithFields(status, ex.getBindingResult().getFieldErrors().stream()
                                                                          .map(field -> String.format("%s - %s",
                                                                                                      field.getField(),
                                                                                                      field.getDefaultMessage())).toList()), status);
    }


    @Override
    protected ResponseEntity<Object> handleMissingServletRequestParameter(MissingServletRequestParameterException ex, HttpHeaders headers,
                                                                          HttpStatus status, WebRequest request) {
        String msg = ex.getMessage();
        return new ResponseEntity<>(new ErrorResponse(status, msg), HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(RecordNotFoundException.class)
    public final ResponseEntity<ErrorResponse> handleRecordNotFoundException(RecordNotFoundException ex) {
        ErrorResponse error = new ErrorResponse(HttpStatus.NOT_FOUND, ex.getLocalizedMessage());
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(DuplicatedMembershipException.class)
    public final ResponseEntity<ErrorResponse> handleDuplicatedMembershipException(DuplicatedMembershipException ex) {
        ErrorResponse error = new ErrorResponse(HttpStatus.CONFLICT, ex.getLocalizedMessage());
        return new ResponseEntity<>(error, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(DuplicatedRoleException.class)
    public final ResponseEntity<ErrorResponse> handleDuplicatedRoleException(DuplicatedRoleException ex) {
        ErrorResponse error = new ErrorResponse(HttpStatus.CONFLICT, ex.getLocalizedMessage());
        return new ResponseEntity<>(error, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(BadRequest.class)
    public final ResponseEntity<ErrorResponse> handleDuplicatedRoleException(BadRequest ex) {
        ErrorResponse error = new ErrorResponse(HttpStatus.BAD_REQUEST, ex.getLocalizedMessage());
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = {javax.validation.ConstraintViolationException.class,
        org.hibernate.exception.ConstraintViolationException.class})
    public final ResponseEntity<ErrorResponse> handleConstraintViolationException(Exception ex) {
        ErrorResponse error = new ErrorResponse(HttpStatus.BAD_REQUEST, "Constraint violation while processing the request");
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);

    }
}
