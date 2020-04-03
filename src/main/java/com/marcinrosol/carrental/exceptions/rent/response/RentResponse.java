package com.marcinrosol.carrental.exceptions.rent.response;

public class RentResponse {
    private String rentResponse;

    public RentResponse(String rentResponse) {
        this.rentResponse = rentResponse;
    }

    public String getRentResponse() {
        return rentResponse;
    }

    public void setRentResponse(String rentResponse) {
        this.rentResponse = rentResponse;
    }
}
