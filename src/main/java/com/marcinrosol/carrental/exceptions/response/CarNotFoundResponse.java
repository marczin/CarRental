package com.marcinrosol.carrental.exceptions.response;

public class CarNotFoundResponse {

    private String CarNotFound;

    public CarNotFoundResponse(String carNotFound) {
        CarNotFound = carNotFound;
    }

    public String getCarNotFound() {
        return CarNotFound;
    }

    public void setCarNotFound(String carNotFound) {
        CarNotFound = carNotFound;
    }
}
