package com.ecore.tempo.exception;

public class DuplicatedMembershipException extends RuntimeException {

    private static final long serialVersionUID = 3244236110056980710L;

    public DuplicatedMembershipException() {
        super("This Membership already exists!");
    }
}
