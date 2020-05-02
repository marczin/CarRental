package com.marcinrosol.carrental.controllers;

import com.marcinrosol.carrental.models.Car;
import com.marcinrosol.carrental.models.Rent;
import com.marcinrosol.carrental.models.RentAdd;
import com.marcinrosol.carrental.services.RentService;
import com.marcinrosol.carrental.validation.MapValidationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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

    /**
     * Function adds rental
     *
     * @param rent rental object
     * @param result errors
     * @return return rent objects
     */
    @PostMapping("/add")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> addRent(@Valid @RequestBody RentAdd rent, BindingResult result){
        ResponseEntity<?> errorMap = mapValidationService.MapValidationService(result);
        if(errorMap != null) return errorMap;

        return new ResponseEntity<Rent>(rentService.addRent(rent), HttpStatus.OK);
    }
    

    //delete rent

    /**
     * Function delete rental by deactivation
     *
     * @param id the id of rental
     */
    @PostMapping("/deactivate")
    @PreAuthorize("hasRole('ADMIN')")
    public void deactivateRent(@RequestParam("id") Long id){
        rentService.deactivateRent(id);
    }

    //get all rents


    /**
     * Function return list of all rentals
     *
     * @return list of rentals
     */
    @GetMapping("/all")
    @PreAuthorize("hasRole('ADMIN')")
    public List<Rent> getAllRents(){
        return rentService.getAllRents();
    }

    /**
     * Function return list of rentals
     *
     * @param page page number (default 0)
     * @param size page size (default 5)
     * @param sorted sorted by (default id)
     * @return List of rentals
     */
    @GetMapping("/list")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<Rent>> carPaginationList(
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "5") Integer size,
            @RequestParam(defaultValue = "id") String sorted
    ){
        return new ResponseEntity<List<Rent>>(rentService.getRentPaginationList(page,size,sorted), HttpStatus.OK);
    }


    /**
     * List of all activated or deactivated rentals
     *
     * @param active boolean
     * @return list of cars
     */
    @GetMapping("/activeList")
    @PreAuthorize("hasRole('ADMIN')")
    public List<Rent> getAllActiveRents(@RequestParam(defaultValue = "true") Boolean active){
        return rentService.getAllByActiveRents(active);
    }


    //get rental by id

    /**
     * Function return rental by id
     * @param id rental id
     * @return rent object
     */
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getRentById(@PathVariable Long id){
        return new ResponseEntity<Rent>(rentService.getById(id), HttpStatus.OK);
    }

    //get rentals by user

    /**
     * Function return rentals by user email
     *
     * @param email user email
     * @return list of rental object
     */
    @GetMapping("/user/{email}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<Rent>> userRents(@PathVariable String email){
        return new ResponseEntity<List<Rent>>(rentService.getAllRentsByUser(email), HttpStatus.OK);
    }

    //get rent by car

    /**
     * Function return list of rentals by vin
     *
     * @param vin vin parameter
     * @return list of rental
     */
    @GetMapping("/car/{vin}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<Rent>> carRents(@PathVariable String vin){
        return new ResponseEntity<List<Rent>>(rentService.getAllRentsByVin(vin), HttpStatus.OK);
    }



}
