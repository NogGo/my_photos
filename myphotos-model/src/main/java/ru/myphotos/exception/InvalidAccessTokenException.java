package ru.myphotos.exception;

public class InvalidAccessTokenException  extends BusinessException {

    public InvalidAccessTokenException(String message) {
        super(message);
    }

}