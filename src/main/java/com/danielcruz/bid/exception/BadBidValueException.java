package com.danielcruz.bid.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "Bad bid value")
public class BadBidValueException extends RuntimeException {
    public BadBidValueException(String message) {
        super(message);
    }
}

