package com.marcinrosol.carrental.controllers;

import com.marcinrosol.carrental.models.Car;
import com.marcinrosol.carrental.models.update.UpdateCar;
import com.marcinrosol.carrental.services.CarService;
import com.marcinrosol.carrental.validation.MapValidationService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/car")
@CrossOrigin("*")
public class CarController {


    private CarService carService;

    private MapValidationService mapValidationService;
    
    @Autowired
    public CarController(CarService carService, MapValidationService mapValidationService) {
        this.carService = carService;
        this.mapValidationService=mapValidationService;
    }

    //get car
    @GetMapping()
    public ResponseEntity<?> getCar(@RequestParam(value = "id", required = true) Long id) {

        return ResponseEntity.ok().body(carService.getCarById(id));
    }

    //Add car

    @PostMapping("/add")
    public ResponseEntity<?> postCar(@Valid @RequestBody Car car, BindingResult result) {
    	ResponseEntity<?> errorMap = mapValidationService.MapValidationService(result);
		if(errorMap != null) return errorMap;
		
    	return new ResponseEntity<Car>(carService.addCar(car), HttpStatus.OK);
    }

    //remove car

    @DeleteMapping("/delete/{id}")
    public void deleteCar(@PathVariable Long id) {
        carService.deleteCar(id);
    }
    //update car

    @PutMapping("/update")
    public ResponseEntity<?> updateCarByVin(@Valid @RequestBody UpdateCar car,
                                            @RequestParam(value = "id", required = false) Long id,
                                            BindingResult result) {
        ResponseEntity<?> errorMap = mapValidationService.MapValidationService(result);
        if(errorMap != null) return errorMap;

        return new ResponseEntity<Car>(carService.updateCar(car, id), HttpStatus.OK);
    }


    //get all cars


    @GetMapping("/all")
    //@PreAuthorize("hasRole('USER')")
    public List<Car> allCars(){
        return carService.getAllCars();
    }

    //get car by vin number
    @GetMapping("/{vin}")
    public ResponseEntity<?> getByVin(@PathVariable String vin){
        return new ResponseEntity<Car>(carService.getCarByVin(vin), HttpStatus.OK);
    }


}
