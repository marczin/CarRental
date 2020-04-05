package com.marcinrosol.carrental.repositories;

import com.marcinrosol.carrental.models.Car;
import com.marcinrosol.carrental.models.Rent;
import com.marcinrosol.carrental.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface RentRepository extends JpaRepository<Rent, Long> {

    List<Rent> findRentByRentedCarAndAndRentedUserAndActive(Car car, User user, Boolean active);
    List<Rent> findRentByRentedCar(Car car);
    List<Rent> findAllByRentedUser(User user);

    List<Rent> findAllByActive(Boolean active);

    List<Rent> findAllByActiveAndReturnedDateIsLessThanEqual(Boolean active, Date date);

    @Modifying
    @Query("UPDATE Rent r set r.active = 0 WHERE r.active = true AND r.returnedDate = :date ")
    void updateActiveOnRent(@Param("date") Date date);

}
