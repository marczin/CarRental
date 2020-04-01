package com.marcinrosol.carrental.exceptions.response;

public class CarArleadyExistResponse {

    private String carArleadyExist;

    public CarArleadyExistResponse(String carArleadyExist) {
        this.carArleadyExist = carArleadyExist;
    }

    public String getCarArleadyExist() {
        return carArleadyExist;
    }

    public void setCarArleadyExist(String carArleadyExist) {
        this.carArleadyExist = carArleadyExist;
    }
}
