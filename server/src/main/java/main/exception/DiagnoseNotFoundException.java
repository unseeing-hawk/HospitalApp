package main.exception;

public class DiagnoseNotFoundException extends RuntimeException {
    public DiagnoseNotFoundException(String message) {
        super(message);
    }
}
