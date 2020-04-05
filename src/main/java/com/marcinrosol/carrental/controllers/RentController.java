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
import java.util.List;

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
    @PostMapping("/deactivate")
    public void deactivateRent(Long id){
        rentService.deactivateRent(id);
    }
    //get all rents
    @GetMapping("/all")
    public List<Rent> getAllRents(){
        return rentService.getAllRents();
    }

    @GetMapping("/all/active")
    public List<Rent> getAllActiveRents(){
        return rentService.getAllActiveRents();
    }
    @GetMapping("/all/notactive")
    public List<Rent> getAllNotActiveRents(){
        return rentService.getAllNotActiveRents();
    }

    //get rent by id
    @GetMapping("/{id}")
    public ResponseEntity<?> getRentById(@PathVariable Long id){
        return new ResponseEntity<Rent>(rentService.getById(id), HttpStatus.OK);
    }

    //get rents by user
    @GetMapping("/user/{email}")
    public List<Rent> userRents(@PathVariable String email){
        return rentService.getAllRentsByUser(email);
    }
    //get rent by car



}
