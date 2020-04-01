package com.marcinrosol.carrental.models;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Rent {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private boolean active;
    private Date rentedDate;
    private Date returnedDate;


    @OneToOne //one way Car dont know anything about relation
    private Car rentedCar;

    //Todo: add cascade delete, if remove rented dont remove any object (remove - archive)
    @OneToOne //USER model dont know aboput rented relations
    private User rentedUser;



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public Date getRentedDate() {
        return rentedDate;
    }

    public void setRentedDate(Date rentedDate) {
        this.rentedDate = rentedDate;
    }

    public Date getReturnedDate() {
        return returnedDate;
    }

    public void setReturnedDate(Date returnedDate) {
        this.returnedDate = returnedDate;
    }

    public Car getRentedCar() {
        return rentedCar;
    }

    public void setRentedCar(Car rentedCar) {
        this.rentedCar = rentedCar;
    }

    public User getRentedUser() {
        return rentedUser;
    }

    public void setRentedUser(User rentedUser) {
        this.rentedUser = rentedUser;
    }
}
