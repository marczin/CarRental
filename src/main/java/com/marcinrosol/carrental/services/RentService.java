package com.marcinrosol.carrental.services;

import com.marcinrosol.carrental.exceptions.car.CarNotFoundException;
import com.marcinrosol.carrental.exceptions.rent.RentDateException;
import com.marcinrosol.carrental.exceptions.rent.RentException;
import com.marcinrosol.carrental.exceptions.user.UserNotFoundException;
import com.marcinrosol.carrental.models.Car;
import com.marcinrosol.carrental.models.Rent;
import com.marcinrosol.carrental.models.RentAdd;
import com.marcinrosol.carrental.models.User;
import com.marcinrosol.carrental.repositories.CarRepository;
import com.marcinrosol.carrental.repositories.RentRepository;
import com.marcinrosol.carrental.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class RentService {
    private UserRepository userRepository;
    private CarRepository carRepository;
    private RentRepository rentRepository;

    @Autowired
    public RentService(UserRepository userRepository, CarRepository carRepository, RentRepository rentRepository) {
        this.userRepository = userRepository;
        this.carRepository = carRepository;
        this.rentRepository = rentRepository;
    }

    /**
     * Function create rent object in database
     *
     * @param rent Rent object
     * @return return rent object
     */
    @Transactional
    public Rent addRent(RentAdd rent) {

        Optional<Car> carOpt = carRepository.findByVin(rent.getVinNumber()); //search car by vin number
        Optional<User> userOpt = userRepository.findByEmail(rent.getEmail()); //search user by email


        if (carOpt.isEmpty())
            throw new CarNotFoundException("Car with vin: '" + rent.getVinNumber() + "' not found!");
        if (userOpt.isEmpty())
            throw new UserNotFoundException("User with email: '" + rent.getEmail() + "' not found!");

        if (!carOpt.get().isActive())
            throw new CarNotFoundException("Car is not active!");

        List<Rent> carRentedList = rentRepository.findRentByRentedCarAndAndRentedUserAndActive(carOpt.get(), userOpt.get(), true);

        if (rent.getRentedDate().compareTo(rent.getReturnedDate()) > 0)
            throw new RentDateException("Rent date cannot be greater than return date!");
        if (rent.getRentedDate().compareTo(rent.getReturnedDate()) == 0)
            throw new RentDateException("Rent and return date cannot equals");

        for (Rent r : carRentedList) {
            if ((rent.getRentedDate().after(r.getRentedDate())) &&
                    (rent.getRentedDate().before(r.getReturnedDate())))
                throw new RentDateException("Car is rented in this time 1");

            if ((rent.getReturnedDate().after(r.getRentedDate())) &&
                    (rent.getReturnedDate().before(r.getReturnedDate())))
                throw new RentDateException("Car is rented in this time 2");
        }

        //create rent
        Rent newRent = new Rent();
        newRent.setRentedUser(userOpt.get());
        newRent.setRentedCar(carOpt.get());
        newRent.setActive(true);
        newRent.setRentedDate(rent.getRentedDate());
        newRent.setReturnedDate(rent.getReturnedDate());

        return rentRepository.saveAndFlush(newRent);
    }

    public List<Rent> getAllRents() {
        return rentRepository.findAll();
    }

    public List<Rent> getAllActiveRents() {

        return rentRepository.findAllByActive(true);
    }

    public List<Rent> getAllNotActiveRents() {
        return rentRepository.findAllByActive(false);
    }

    public Rent getById(Long id) {
        Optional<Rent> opt = rentRepository.findById(id);
        if (opt.isPresent()) return opt.get();
        throw new RentException("Rent does not exist");
    }

    public List<Rent> getAllRentsByUser(String email) {
        String regex = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);
        if(!matcher.matches()) throw new UserNotFoundException("Email is valid!"); //todo: add custom exception

        Optional<User> opt = userRepository.findByEmail(email);
        if(opt.isEmpty()) throw new UserNotFoundException("User not found!");

        return rentRepository.findAllByRentedUser(opt.get());
    }

    @Transactional
    public void deactivateRent(Long id) {
        Optional<Rent> opt = rentRepository.findById(id);
        if(opt.isEmpty()) throw new RentException("Can't find rent with id: '"+id+"'");
        opt.get().setActive(false);
    }

    @Transactional
    public void updateActiveOnReturnedByDate(Date date){
        List<Rent> rentList = rentRepository.findAllByActiveAndReturnedDateIsLessThanEqual(true, date);
        rentList.forEach( o -> o.setActive(false));
        rentRepository.saveAll(rentList);
    }
}
