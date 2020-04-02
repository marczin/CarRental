package com.marcinrosol.carrental.services;

import com.marcinrosol.carrental.exceptions.user.UserAlreadyExistException;
import com.marcinrosol.carrental.exceptions.user.UserNotFoundException;
import com.marcinrosol.carrental.models.User;
import com.marcinrosol.carrental.repositories.CarRepository;
import com.marcinrosol.carrental.repositories.RentRepository;
import com.marcinrosol.carrental.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.List;
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


    /**
     * Function add user object into database
     *
     * @param user User object
     * @return return saved object
     */
    public User addUser(User user) {
        //todo: add validation
        Optional<User> fetchUser = userRepository.findByName(user.getName());
        if (fetchUser.isPresent()) throw new UserAlreadyExistException("User already exist!");
        return userRepository.saveAndFlush(user);
    }

    /**
     * Function update existing user object
     *
     * @param user user object
     * @return retun updated object
     */
    public User updateUser(User user) {
        //todo: VALIDATE USER
        Optional<User> opt = userRepository.findByName(user.getName());
        if (opt.isPresent()) {
            return userRepository.saveAndFlush(user);
        }
        throw new UserNotFoundException("User not found!");
    }

    /**
     * Function delete user from database by id
     *
     * @param id user id
     */
    public void deleteUser(Long id) {
        Optional<User> opt = userRepository.findById(id);
        if(opt.isPresent()) userRepository.deleteById(id);
        throw new UserNotFoundException("User not found!");
    }

    /**
     * Function fetch user object from database by id
     *
     * @param id user id
     * @return fetched user
     */
    public User getUserById(Long id) {
        Optional<User> opt = userRepository.findById(id);
        if(opt.isPresent()) return opt.get();
        throw new UserNotFoundException("User not found!");
    }

    /**
     * Function fetch all users from database
     *
     * @return List of users
     */
    public List<User> getAllUser() {
        return userRepository.findAll();
    }
}
