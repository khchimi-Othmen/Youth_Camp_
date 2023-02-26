package org.esprit.storeyc.exception;

import lombok.Getter;

import java.util.List;

public class InvalidEntityException extends RuntimeException{
    @Getter
    private ErrorCodes errorCodes;
    @Getter
    private List<String> errors;

    private InvalidEntityException(String message){
        super(message);
    }
    private InvalidEntityException(String message,Throwable cause){
        super(message,cause);
    }
    private InvalidEntityException(String message,Throwable cause,ErrorCodes errorCodes){
        super(message,cause);//3leh super mouch this.
        this.errorCodes =errorCodes;
    }
    private InvalidEntityException(String message,ErrorCodes errorCodes){
        super(message);
        this.errorCodes = errorCodes;
    }
    public InvalidEntityException(String message,ErrorCodes errorCodes,List<String> errors){
        super(message);
        this.errorCodes = errorCodes;
        this.errors = errors;
    }
}
