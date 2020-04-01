package com.marcinrosol.carrental.exceptions.car;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class CarAlreadyExistException extends RuntimeException {

    public CarAlreadyExistException(String message) {
        super(message);
    }
}
