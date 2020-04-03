package com.marcinrosol.carrental.repositories;

import com.marcinrosol.carrental.models.Car;
import com.marcinrosol.carrental.models.Rent;
import com.marcinrosol.carrental.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RentRepository extends JpaRepository<Rent, Long> {

    List<Rent> findRentByRentedCarAndAndRentedUser(Car car, User user);
    Optional<Rent> findRentByRentedCar(Car car);
}
