package com.marcinrosol.carrental.exceptions.user.response;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


public class UserAlreadyExistResponse{

    private String UserAlreadyExistResponse;

    public UserAlreadyExistResponse(String userAlreadyExistResponse) {
        UserAlreadyExistResponse = userAlreadyExistResponse;
    }

    public String getUserAlreadyExistResponse() {
        return UserAlreadyExistResponse;
    }

    public void setUserAlreadyExistResponse(String userAlreadyExistResponse) {
        UserAlreadyExistResponse = userAlreadyExistResponse;
    }
}
