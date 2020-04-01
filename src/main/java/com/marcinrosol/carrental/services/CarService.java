package com.marcinrosol.carrental.services;

import com.marcinrosol.carrental.exceptions.car.CarAlreadyExistException;
import com.marcinrosol.carrental.exceptions.car.CarNotFoundException;
import com.marcinrosol.carrental.models.Car;
import com.marcinrosol.carrental.repositories.CarRepository;
import com.marcinrosol.carrental.repositories.RentRepository;
import com.marcinrosol.carrental.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
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
            throw new CarAlreadyExistException("Car already exist!");
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

            throw new CarAlreadyExistException("Car arleady exist!");
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
        throw new CarNotFoundException("Car not found!");
    }
}
