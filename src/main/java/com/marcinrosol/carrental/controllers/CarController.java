package com.marcinrosol.carrental.controllers;

import com.marcinrosol.carrental.models.Car;
import com.marcinrosol.carrental.services.CarService;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/car")
@CrossOrigin("*")
public class CarController {


    private CarService carService;

    @Autowired
    public CarController(CarService carService) {
        this.carService = carService;
    }

    //get car
    @GetMapping("/{id}")
    public ResponseEntity<?> getCar(@PathVariable("id") Long id) {
        return ResponseEntity.ok().body(carService.getCarById(id));
    }

    //Add car

    @PostMapping("/add")
    public ResponseEntity<?> postCar(@RequestBody Car car) {
        return new ResponseEntity<Car>(carService.addCar(car), HttpStatus.OK);
    }

    //remove car

    @DeleteMapping("/delete/{id}")
    public void deleteCar(@PathVariable Long id) {
        carService.deleteCar(id);
    }
    //update car

    @PutMapping("/update")
    public ResponseEntity<?> updateCar(@RequestBody Car car) {

        return new ResponseEntity<Car>(carService.updateCar(car), HttpStatus.OK);
    }


}