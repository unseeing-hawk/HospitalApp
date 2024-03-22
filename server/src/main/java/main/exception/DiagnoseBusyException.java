package main.exception;

public class DiagnoseBusyException extends RuntimeException {
    public DiagnoseBusyException(String message) {
        super(message);
    }
}
