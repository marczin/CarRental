package com.marcinrosol.carrental.exceptions.rent;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class RentException extends RuntimeException{
    public RentException(String message) {
        super(message);
    }
}
