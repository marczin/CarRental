package com.marcinrosol.carrental.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.marcinrosol.carrental.models.Enums.CarType;
import com.marcinrosol.carrental.validation.enums.CarTypeSubset;
import com.marcinrosol.carrental.validation.enums.EnumNamePattern;
import com.marcinrosol.carrental.validation.enums.ValueOfEnum;
import org.springframework.format.annotation.NumberFormat;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.Date;

@Entity
public class Car {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message="model can not be empty")
    @NotNull(message="model can not be null")
    private String model;
    @NotBlank(message="mark can not be empty")
    @NotNull(message="model can not be null")
    private String mark;

    @NotNull(message="range can not be null")
    @NumberFormat
    @Min(value = 0)
    @Max(value = 999999999)
    private Long kmPassed;

    private String details; //Details like interior colour etc

    @NotNull(message="date can not be null")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date prodDate; //Date of production

    @NotNull(message = "seats can not be null")
    @Min(value = 1)
    @Max(value = 10)
    private int seats; // number of seats

    //@ValueOfEnum(enumClass = CarType.class)
    //@CarTypeSubset(anyOf = {CarType.Asegment, CarType.Bsegment, CarType.Csegment, CarType.Dsegment, CarType.Esegment, CarType.Fsegment}, message = "wrong!")
    @NotNull
    @EnumNamePattern(regexp = "Asegment|Bsegment|Csegment|Dsegment|Esegment|Fsegment" ) //todo: not showing message :(
    private CarType carType; //segment class

    @NotNull
    @NotBlank
    @Size(max = 17, min = 17, message = "vin number require 17 digits")
    @Column(unique = true)
    private String vin;

    @NotNull
    private Boolean active;

    public Boolean isActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public String getVin() {
        return vin;
    }

    public void setVin(String vin) {
        this.vin = vin;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getMark() {
        return mark;
    }

    public void setMark(String mark) {
        this.mark = mark;
    }

    public Long getKmPassed() {
        return kmPassed;
    }

    public void setKmPassed(Long kmPassed) {
        this.kmPassed = kmPassed;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public Date getProdDate() {
        return prodDate;
    }

    public void setProdDate(Date prodDate) {
        this.prodDate = prodDate;
    }

    public int getSeats() {
        return seats;
    }

    public void setSeats(int seats) {
        this.seats = seats;
    }

    public CarType getCarType() {
        return carType;
    }

    public void setCarType(CarType carType) {
        this.carType = carType;
    }

    @Override
    public String toString() {
        return "Car{" +
                "id=" + id +
                ", model='" + model + '\'' +
                ", mark='" + mark + '\'' +
                ", kmPassed=" + kmPassed +
                ", details='" + details + '\'' +
                ", prodDate=" + prodDate +
                ", seats=" + seats +
                ", carType=" + carType +
                ", vin='" + vin + '\'' +
                ", active=" + active +
                '}';
    }
}
