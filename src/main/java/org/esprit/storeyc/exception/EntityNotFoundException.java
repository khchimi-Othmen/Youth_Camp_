package org.esprit.storeyc.exception;

import lombok.Getter;

public class EntityNotFoundException extends RuntimeException{
    @Getter
    private ErrorCodes errorCodes;

    private EntityNotFoundException(String message){
        super(message);
    }
        private EntityNotFoundException(String message, Throwable cause){
        super(message,cause);//3awdhet this.
    }
        private EntityNotFoundException(String message, Throwable cause, ErrorCodes errorCodes){
        super(message,cause);
        this.errorCodes =errorCodes;
    }
    public EntityNotFoundException(String message, ErrorCodes errorCodes){
        super(message);
        this.errorCodes = errorCodes;
    }
}
