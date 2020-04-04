package com.marcinrosol.carrental.services;

import com.marcinrosol.carrental.exceptions.car.CarAlreadyExistException;
import com.marcinrosol.carrental.exceptions.car.CarNotFoundException;
import com.marcinrosol.carrental.models.Car;
import com.marcinrosol.carrental.models.Enums.CarType;
import com.marcinrosol.carrental.models.update.UpdateCar;
import com.marcinrosol.carrental.repositories.CarRepository;
import com.marcinrosol.carrental.repositories.RentRepository;
import com.marcinrosol.carrental.repositories.UserRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.List;
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
    @Transactional
    public Car getCarById(Long id) {
        Optional<Car> fetchedCar = carRepository.findById(id);
        if (fetchedCar.isPresent()) {
            return fetchedCar.get();
        }
        throw new CarNotFoundException("Car not found");
    }

    //todo: add object validate

    /**
     * Function add Car object to database
     *
     * @param car Object of car
     * @return return saved object
     */
    @Transactional
    public Car addCar(Car car) {
        Optional<Car> opt = carRepository.findByVin(car.getVin());
        if (opt.isPresent()) {

            throw new CarAlreadyExistException("Car already exist!");
        }
        //car.setCarType(CarType.Asegment);
        if(!opt.get().isActive()){

        }
        return carRepository.save(car);
    }

    /**
     * Function delete Car object from database by id
     *
     * @param id id of car object
     */
    @Transactional
    public void deleteCar(Long id) {
        Optional<Car> opt = carRepository.findById(id);
        if (opt.isEmpty()) {
            throw new CarAlreadyExistException("Car not exist!");
        }
        opt.get().setActive(false);
        carRepository.saveAndFlush(opt.get());
    }

    /**
     * Function update existing car object. In case we provide id, service will be searching database by id
     * in other case object will be fetched by vin number.
     * If you want update vin number you need to use id parameter.
     *
     * @param car object with updated value
     * @param id  id of object, is not required.
     * @return return updated object
     */
    @Transactional
    public Car updateCar(UpdateCar car, Long id) {
        //todo: validate
        if (id == null) {
            Optional<Car> opt = carRepository.findByVin(car.getVin());
            if (opt.isPresent()) {
                BeanUtils.copyProperties(car, opt.get());
                opt.get().setId(id);
                return carRepository.saveAndFlush(opt.get());
            }
            throw new CarNotFoundException("Car with vin number: '" + car.getVin() + "' not found!");
        } else {
            Optional<Car> opt = carRepository.findById(id);
            if (opt.isPresent()) {
                BeanUtils.copyProperties(car, opt.get());
                opt.get().setId(id);
                return carRepository.saveAndFlush(opt.get());
            }
            throw new CarNotFoundException("Car with id number: '" + id + "' not found!");
        }

    }


    /**
     * Function return all cars from database
     *
     * @return Car list
     */
    @Transactional
    public List<Car> getAllCars() {
        return carRepository.findAll();
    }

    /**
     * Function return object of car by vin parameter
     *
     * @param vin vin parameter
     * @return return car object from database
     */
    @Transactional
    public Car getCarByVin(String vin) {
        Optional<Car> opt = carRepository.findByVin(vin);
        if (opt.isPresent()) return opt.get();

        throw new CarNotFoundException("Car not found!");
    }
}
