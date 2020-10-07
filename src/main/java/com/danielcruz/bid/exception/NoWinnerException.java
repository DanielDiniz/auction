package com.danielcruz.bid.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "No winner found")
public class NoWinnerException extends RuntimeException {
    public NoWinnerException(String message) {
        super(message);
    }
}
