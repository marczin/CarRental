package com.marcinrosol.carrental.services;

import com.marcinrosol.carrental.exceptions.user.UserAlreadyExistException;
import com.marcinrosol.carrental.exceptions.user.UserNotFoundException;
import com.marcinrosol.carrental.models.User;
import com.marcinrosol.carrental.models.update.UpdateUser;
import com.marcinrosol.carrental.repositories.CarRepository;
import com.marcinrosol.carrental.repositories.RentRepository;
import com.marcinrosol.carrental.repositories.UserRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    /**
     * Function add user object into database
     *
     * @param user User object
     * @return return saved object
     */
    @Transactional
    public User addUser(User user) {
        Optional<User> fetchUser = userRepository.findByEmail(user.getEmail());
        if (fetchUser.isPresent()) throw new UserAlreadyExistException("User already exist!");
        return userRepository.saveAndFlush(user);
    }

    /** TODO: UPDATE IT LATER TO ADD PASSWORD CHANGE
     * Function update existing user object
     *
     * @param user user object
     * @return retun updated object
     */
    @Transactional
    public User updateUser(UpdateUser user) {

        Optional<User> opt = userRepository.findByEmail(user.getEmail());
        if (opt.isPresent()) {
            BeanUtils.copyProperties(user, opt.get());
            return userRepository.saveAndFlush(opt.get());
        }
        throw new UserNotFoundException("User with email: '" + user.getEmail() + "' not found!");
    }

    /**
     * Function delete user from database by id
     *
     * @param id user id
     */
    @Transactional
    public void deleteUser(Long id) {
        Optional<User> opt = userRepository.findById(id);
        if (opt.isPresent()) userRepository.deleteById(id);
        throw new UserNotFoundException("User with id: '" + id + "' not found!");
    }

    /**
     * Function fetch user object from database by id
     *
     * @param id user id
     * @return fetched user
     */
    @Transactional
    public User getUserById(Long id) {
        Optional<User> opt = userRepository.findById(id);
        if (opt.isPresent()) return opt.get();
        throw new UserNotFoundException("User with id: '" + id + "' not found!");
    }

    /**
     * Function fetch all users from database
     *
     * @return List of users
     */
    @Transactional
    public List<User> getAllUser() {
        return userRepository.findAll();
    }
}
