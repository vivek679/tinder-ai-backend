package com.learning.tinderaibackend.exceptions;

public class RedirectException extends RuntimeException {

    public RedirectException() {
        super("Redirection required!!");
    }

    public RedirectException(String message) {
        super(message);
    }

    public RedirectException(String message, Throwable cause) {
        super(message, cause);
    }

}
