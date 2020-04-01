package com.marcinrosol.carrental.exceptions.response;

public class CarAlreadyExistResponse {

    private String carArleadyExist;

    public CarAlreadyExistResponse(String carArleadyExist) {
        this.carArleadyExist = carArleadyExist;
    }

    public String getCarArleadyExist() {
        return carArleadyExist;
    }

    public void setCarArleadyExist(String carArleadyExist) {
        this.carArleadyExist = carArleadyExist;
    }
}
