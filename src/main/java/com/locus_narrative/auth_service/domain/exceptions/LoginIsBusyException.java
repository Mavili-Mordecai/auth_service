package com.locus_narrative.auth_service.domain.exceptions;

public class LoginIsBusyException extends Exception {
    public LoginIsBusyException(String message) {
        super(message);
    }
}
