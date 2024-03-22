package main.exception;

public class PeopleNotFoundException extends RuntimeException {
    public PeopleNotFoundException(String message) {
        super(message);
    }
}
