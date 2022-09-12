package com.ecore.tempo.exception;

public class DuplicatedRoleException extends RuntimeException {

    private static final long serialVersionUID = 3244236110056980710L;

    public DuplicatedRoleException(String name) {
        super("Role with the name: " + name + " already exists!");
    }
}
