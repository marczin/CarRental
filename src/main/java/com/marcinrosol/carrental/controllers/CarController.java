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

    /**
     * Function return car object from database
     *
     * @param id car id
     * @return car object
     */
    @GetMapping()
    //@PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> getCar(@RequestParam(value = "id", required = true) Long id) {

        return ResponseEntity.ok().body(carService.getCarById(id));
    }

    //Add car

    /**
     * Function adds car object into db
     *
     * @param car car object
     * @param result errors
     * @return return added object
     */
    @PostMapping("/add")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> postCar(@Valid @RequestBody Car car, BindingResult result) {
    	ResponseEntity<?> errorMap = mapValidationService.MapValidationService(result);
		if(errorMap != null) return errorMap;
		
    	return new ResponseEntity<Car>(carService.addCar(car), HttpStatus.OK);
    }

    //remove car

    /**
     * Function delete car object rom db by id
     * @param id id
     */
    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public void deleteCar(@PathVariable Long id) {
        carService.deleteCar(id);
    }

    //update car

    /**
     *  Function update car object. In case we provide id, service will be searching database by id
     *  in other case object will be fetched by vin number.
     *
     * @param car updated car object
     * @param id optional id
     * @param result errors
     * @return updated object
     */
    @PutMapping("/update")
    @PreAuthorize("hasRole('ADMIN')")
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
    public ResponseEntity<List<Car>> allCars(){
        return new ResponseEntity<List<Car>>(carService.getAllCars(), HttpStatus.OK);
    }

    //get cars list pagination

    /**
     * Function return list of cars
     *
     * @param page page number (default 0)
     * @param size page size (default 5)
     * @param sorted sorted by (default id)
     * @return List of cars
     */
    @GetMapping("/list")
    //@PreAuthorize("hasRole('USER')")
    public ResponseEntity<List<Car>> carPaginationList(
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "5") Integer size,
            @RequestParam(defaultValue = "id") String sorted
    ){
        return new ResponseEntity<List<Car>>(carService.getCarPaginationList(page,size,sorted), HttpStatus.OK);
    }



    //get car by vin number

    /**
     * Function get car by vin
     *
     * @param vin vin number
     * @return car object
     */
    @GetMapping("/{vin}")
    //@PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> getByVin(@PathVariable String vin){
        return new ResponseEntity<Car>(carService.getCarByVin(vin), HttpStatus.OK);
    }


}
