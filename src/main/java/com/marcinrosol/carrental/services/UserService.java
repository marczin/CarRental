package com.marcinrosol.carrental.services;

import com.marcinrosol.carrental.models.User;
import com.marcinrosol.carrental.repositories.CarRepository;
import com.marcinrosol.carrental.repositories.RentRepository;
import com.marcinrosol.carrental.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private UserRepository userRepository;
    private CarRepository carRepository;
    private RentRepository rentRepository;

    @Autowired
    public UserService(UserRepository userRepository, CarRepository carRepository, RentRepository rentRepository) {
        this.userRepository = userRepository;
        this.carRepository = carRepository;
        this.rentRepository = rentRepository;
    }


    public User addUser(User user) {
        //todo: add validation
        Optional<User> fetchUser = userRepository.findByName(user.getName());
        if(fetchUser.isPresent()) //throw new UserAlreadyExistException();
        return userRepository.saveAndFlush(user);
    }
}
