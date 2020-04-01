package com.marcinrosol.carrental.controllers;

import com.marcinrosol.carrental.models.Car;
import com.marcinrosol.carrental.models.Enums.CarType;
import com.marcinrosol.carrental.repositories.CarRepository;
import com.marcinrosol.carrental.services.CarService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.text.SimpleDateFormat;
import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CarControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Autowired
    private CarService carService;

    @Autowired
    private CarRepository carRepository;

    @Test
    void getCar() throws Exception {
        Car car = new Car();
        car.setCarType(CarType.Asegment);
        car.setDetails("details");
        car.setKmPassed(123L);
        car.setMark("BMW");
        car.setModel("E46");
        SimpleDateFormat sdf = new SimpleDateFormat("YYY-MM-DD");
        String dateInString = "2020-01-01";
        Date date = sdf.parse(dateInString);
        car.setProdDate(date);
        car.setSeats(4);
        car.setVin(123456789L);

        carService.addCar(car);
        Car car2 = carRepository.findByVin(car.getVin()).get();
        car.setId(car2.getId());

        ResponseEntity<Car> response = testRestTemplate.postForEntity("/api/car?id=" + car.getId(), car, Car.class);

        assertEquals(car.getId(), car2.getId());
        assertEquals(car.getVin(), car2.getVin());
        assertEquals(car.getProdDate(), car2.getProdDate());
    }

    @Test
    void postCar() {
    }

    @Test
    void deleteCar() {
    }

    @Test
    void updateCar() {
    }
}
