package com.fastcampus.boardserver.exception;

public class DuplicateException extends RuntimeException {

    public DuplicateException(String msg) {
        super(msg);
    }
}
