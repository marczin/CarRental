package com.marcinrosol.carrental.exceptions.rent.response;

public class RentDateResponse {

    private String rentDate;

    public RentDateResponse(String rentDate) {
        this.rentDate = rentDate;
    }

    public String getRentDate() {
        return rentDate;
    }

    public void setRentDate(String rentDate) {
        this.rentDate = rentDate;
    }
}

