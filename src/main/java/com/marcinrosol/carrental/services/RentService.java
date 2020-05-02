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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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

            carOpt.orElseThrow(()-> new CarNotFoundException("Car with vin: '" + rent.getVinNumber() + "' not found!"));
            userOpt.orElseThrow(()-> new UserNotFoundException("User with email: '" + rent.getEmail() + "' not found!"));

            carOpt.filter(o -> o.isActive()).orElseThrow(() -> new CarNotFoundException("Car is not active!"));
//        if (!carOpt.get().isActive())
//            throw new CarNotFoundException("Car is not active!");

        //list of rents
        List<Rent> carRentedList = rentRepository.findRentByRentedCarAndAndRentedUserAndActive(carOpt.get(), userOpt.get(), true);

        //check if return date is not before rent date
        if (rent.getRentedDate().compareTo(rent.getReturnedDate()) > 0)
            throw new RentDateException("Rent date cannot be greater than return date!");
        if (rent.getRentedDate().compareTo(rent.getReturnedDate()) == 0)
            throw new RentDateException("Rent and return date cannot equals");

        //check if car is not already rented in this dates
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

    /**
     * Function return all rents
     * @return list of rents
     */
    public List<Rent> getAllRents() {
        return rentRepository.findAll();
    }

    /**
     * Function return all rents by active boolean
     * @param active active parameter
     * @return list of rents
     */
    public List<Rent> getAllByActiveRents(Boolean active) {
        return rentRepository.findAllByActive(active);
    }


    /**
     * Function return rent by id
     *
     * @param id id parameter
     * @return rent object
     */
    public Rent getById(Long id) {
        Optional<Rent> opt = rentRepository.findById(id);
        opt.orElseThrow(() -> new RentException("Rent does not exist"));
        return opt.get();
    }

    /**
     * Function return list of rents by user email
     *
     * @param email email
     * @return list of rents
     */
    public List<Rent> getAllRentsByUser(String email) {
        //this will check if email is email
        String regex = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);
        if(!matcher.matches()) throw new UserNotFoundException("Email is valid!"); //todo: add custom exception

        Optional<User> opt = userRepository.findByEmail(email);
        opt.orElseThrow(() -> new UserNotFoundException("User not found!"));
        //if(opt.isEmpty()) throw new UserNotFoundException("User not found!");

        return rentRepository.findAllByRentedUser(opt.get());
    }

    /**
     * Function set active status to false
     * @param id id parameter
     */
    @Transactional
    public void deactivateRent(Long id) {
        Optional<Rent> opt = rentRepository.findById(id);
        opt.orElseThrow(()-> new RentException("Can't find rent with id: '"+id+"'"));
        opt.get().setActive(false);
        rentRepository.saveAndFlush(opt.get());
    }

    /**
     * Function set active status to false to all objects with date
     * @param date date parameter
     */
    @Transactional
    public void updateActiveOnReturnedByDate(Date date){
        List<Rent> rentList = rentRepository.findAllByActiveAndReturnedDateIsLessThanEqual(true, date);
        rentList.forEach( o -> o.setActive(false));
        rentRepository.saveAll(rentList);
    }

    /**
     * Function return list of rents by vin
     * @param vin vin parameter
     * @return list of rents
     */
    @Transactional
    public List<Rent> getAllRentsByVin(String vin) {
        return rentRepository.findAllByVin(vin);
    }

    /**
     * Function return rent list by page number, size and sort
     *
     * @param page page number
     * @param size how many objects, default is 5
     * @param sorted sorted parameter, default is by id
     * @return list of rent objects
     */
    @Transactional
    public List<Rent> getRentPaginationList(Integer page, Integer size, String sorted) {
        Pageable paging = PageRequest.of(page,size, Sort.by(sorted));
        Page<Rent> result = rentRepository.findAll(paging);
        return result.getContent();
    }
}
