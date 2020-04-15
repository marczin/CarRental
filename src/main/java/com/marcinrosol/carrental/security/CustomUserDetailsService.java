package com.marcinrosol.carrental.security;

import com.marcinrosol.carrental.exceptions.user.UserNotFoundException;
import com.marcinrosol.carrental.models.User;
import com.marcinrosol.carrental.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private UserRepository userRepository;

    @Autowired
    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    /**
     * Function load user by Username
     *
     * @param username the username
     * @return return UserDetails object
     * @throws UsernameNotFoundException
     */
    @Transactional
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsernameOrEmail(username, username).orElseThrow(() ->
                new UsernameNotFoundException("User not found!")
        );
        return UserPrincipal.create(user);
    }

    /**
     * Function load user by id
     *
     * @param id the id
     * @return return UserDetails object
     */
    @Transactional
    public UserDetails loadUserById(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("user not found"));
        return UserPrincipal.create(user);
    }
}
