package com.pankova.hospitalserver.exception;

public class WardBusyException extends RuntimeException{
    public WardBusyException(String message) {
        super(message);
    }
}
