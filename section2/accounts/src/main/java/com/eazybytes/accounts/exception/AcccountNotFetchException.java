package com.eazybytes.accounts.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class AcccountNotFetchException extends RuntimeException{
    public AcccountNotFetchException(String message) {
        super(message);
    }
}


