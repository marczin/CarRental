package com.marcinrosol.carrental.controllers;

import com.marcinrosol.carrental.models.Rent;
import com.marcinrosol.carrental.models.RentAdd;
import com.marcinrosol.carrental.services.RentService;
import com.marcinrosol.carrental.validation.MapValidationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/rent")
@CrossOrigin("*")
public class RentController {

    private RentService rentService;
    private MapValidationService mapValidationService;

    public RentController(RentService rentService, MapValidationService mapValidationService) {
        this.rentService = rentService;
        this.mapValidationService=mapValidationService;
    }

    //add new rent

    @PostMapping("/add")
    public ResponseEntity<?> addRent(@Valid @RequestBody RentAdd rent, BindingResult result){
        ResponseEntity<?> errorMap = mapValidationService.MapValidationService(result);
        if(errorMap != null) return errorMap;

        return new ResponseEntity<Rent>(rentService.addRent(rent), HttpStatus.OK);
    }
    //update rent

    //delete rent

    //get all rents

    //get rent by id

    //get rents by user

    //get rent by car



}
