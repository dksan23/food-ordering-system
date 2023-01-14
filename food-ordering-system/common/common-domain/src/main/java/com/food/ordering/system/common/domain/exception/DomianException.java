package com.food.ordering.system.common.domain.exception;

public class DomianException extends  RuntimeException{
    public DomianException(String message) {
        super(message);
    }

    public DomianException(String message, Throwable cause) {
        super(message, cause);
    }
}
