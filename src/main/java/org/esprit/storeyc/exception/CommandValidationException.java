package org.esprit.storeyc.exception;

import java.util.List;

public class CommandValidationException extends RuntimeException {

    private final ErrorCodes errorCode;
    private final List<String> errors;

    public CommandValidationException(String message, ErrorCodes errorCode, List<String> errors) {
        super(message);
        this.errorCode = errorCode;
        this.errors = errors;
    }

    public ErrorCodes getErrorCode() {
        return errorCode;
    }

    public List<String> getErrors() {
        return errors;
    }
}
