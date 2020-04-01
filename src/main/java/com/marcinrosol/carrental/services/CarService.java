package com.marcinrosol.carrental.services;

import com.marcinrosol.carrental.models.Car;
import com.marcinrosol.carrental.repositories.CarRepository;
import com.marcinrosol.carrental.repositories.RentRepository;
import com.marcinrosol.carrental.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CarService {

    private UserRepository userRepository;
    private CarRepository carRepository;
    private RentRepository rentRepository;

    @Autowired
    public CarService(UserRepository userRepository, CarRepository carRepository, RentRepository rentRepository) {
        this.userRepository = userRepository;
        this.carRepository = carRepository;
        this.rentRepository = rentRepository;
    }

    /**
     * Function fetch Car object from database
     *
     * @param id id of fetched oibject
     * @return return fetched car object
     */
    public Object getCarById(Long id) {
        return carRepository.getOne(id);
    }

    //todo: add validate

    /**
     * Function add Car object to database
     *
     * @param car Object of car
     * @return return saved object
     */
    public Car addCar(Car car) {
        Optional<Car> opt = carRepository.findById(car.getId());
        if (opt.isPresent()) {
            //todo: throw new CarArleadyExistException();
        }
        return carRepository.saveAndFlush(car);
    }

    /**
     * Function delete Car object from database by id
     *
     * @param id id of car object
     */
    public void deleteCar(Long id) {
        Optional<Car> opt = carRepository.findById(id);
        if (opt.isPresent()) {
            //todo: throw new CarArleadyExistException();
        }
        carRepository.deleteById(id);
    }

    /**
     * Function update existing car object
     *
     * @param car object with updated vlaue
     * @return return updated object
     */
    public Car updateCar(Car car) {
        //todo: validate
        Optional<Car> opt = carRepository.findById(car.getId());
        if (opt.isPresent()) {
            return carRepository.saveAndFlush(car);
        }
        return null;  //todo: throw new CarNotFoundException();
    }
}
