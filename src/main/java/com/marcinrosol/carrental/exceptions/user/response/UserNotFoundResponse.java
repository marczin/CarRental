package com.marcinrosol.carrental.exceptions.user.response;

public class UserNotFoundResponse {
    private String userNotFoundResponse;

    public UserNotFoundResponse(String userNotFoundResponse) {
        this.userNotFoundResponse = userNotFoundResponse;
    }

    public String getUserNotFoundResponse() {
        return userNotFoundResponse;
    }

    public void setUserNotFoundResponse(String userNotFoundResponse) {
        this.userNotFoundResponse = userNotFoundResponse;
    }
}
