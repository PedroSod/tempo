package com.ecore.tempo.exception;

public class RecordNotFoundException extends RuntimeException {

    private static final long serialVersionUID = 3244236110056980710L;

    public RecordNotFoundException(String entity, String id) {
        super("No "+entity+" found for id : " + id);
    }
}
