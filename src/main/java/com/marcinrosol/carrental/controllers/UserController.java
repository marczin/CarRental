package com.marcinrosol.carrental.controllers;

import com.marcinrosol.carrental.models.User;
import com.marcinrosol.carrental.models.update.UpdateCar;
import com.marcinrosol.carrental.models.update.UpdateUser;
import com.marcinrosol.carrental.services.UserService;
import com.marcinrosol.carrental.validation.MapValidationService;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Repository;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/user")
@CrossOrigin("*")
public class UserController {

    private UserService userService;
    private MapValidationService mapValidationService;

    @Autowired
    public UserController(UserService userService, MapValidationService mapValidationService) {
        this.userService = userService;
        this.mapValidationService = mapValidationService;
    }

    /*
    //add user
    @PostMapping("/add")
    public ResponseEntity<?> addUser(@Valid @RequestBody User user, BindingResult result){
        ResponseEntity<?> errorMap = mapValidationService.MapValidationService(result);
        if(errorMap != null) return errorMap;

        return new ResponseEntity<User>(userService.addUser(user), HttpStatus.OK);
    }
    */

    //todo: change (or add function) to update self information, it's not safe in this way
    //update user
    @PutMapping("/update")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> updateUser(@RequestBody UpdateUser user, BindingResult result){
        ResponseEntity<?> errorMap = mapValidationService.MapValidationService(result);
        if(errorMap != null) return errorMap;

        return new ResponseEntity<User>(userService.updateUser(user), HttpStatus.OK);
    }

    //delete user
    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public void deleteUser(@PathVariable Long id){
        userService.deleteUser(id);
    }

    //getuser
    @GetMapping()
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getUser(@RequestParam(value = "id", required = true) Long id){
        return new ResponseEntity<User>(userService.getUserById(id), HttpStatus.OK);
    }
    //getallusers

    @GetMapping("/all")
    @PreAuthorize("hasRole('ADMIN')")
    public List<User> getAllUser(){
        return userService.getAllUser();
    }

}
