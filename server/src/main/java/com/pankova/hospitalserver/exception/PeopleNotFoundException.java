package com.pankova.hospitalserver.exception;

public class PeopleNotFoundException extends RuntimeException {
    public PeopleNotFoundException(String message) {
        super(message);
    }
}
