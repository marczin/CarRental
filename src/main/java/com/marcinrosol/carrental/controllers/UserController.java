package com.marcinrosol.carrental.controllers;

import com.marcinrosol.carrental.models.User;
import com.marcinrosol.carrental.services.UserService;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
@CrossOrigin("*")
public class UserController {

    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    //add user
    @PostMapping("/add")
    public ResponseEntity<?> addUser(@RequestBody User user){
        return new ResponseEntity<User>(userService.addUser(user), HttpStatus.OK);
    }

    //update user

    //delete user

    //getuser

    //getallusers

}
