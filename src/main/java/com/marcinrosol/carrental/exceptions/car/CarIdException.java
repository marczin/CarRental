package com.marcinrosol.carrental.exceptions.car;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class CarIdException extends RuntimeException {

    public CarIdException(String message){
        super(message);
    }
}
