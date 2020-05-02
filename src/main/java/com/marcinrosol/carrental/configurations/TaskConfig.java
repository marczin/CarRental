package com.marcinrosol.carrental.configurations;

import com.marcinrosol.carrental.models.Car;
import com.marcinrosol.carrental.models.Enums.CarType;
import com.marcinrosol.carrental.models.Enums.RoleName;
import com.marcinrosol.carrental.models.Rent;
import com.marcinrosol.carrental.models.Role;
import com.marcinrosol.carrental.repositories.CarRepository;
import com.marcinrosol.carrental.repositories.RentRepository;
import com.marcinrosol.carrental.repositories.RoleRepository;
import com.marcinrosol.carrental.services.RentService;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import javax.transaction.Transactional;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Configuration
@EnableScheduling
public class TaskConfig {


    private RentService rentService;
    private RoleRepository roleRepository;
    private CarRepository carRepository;

    @Autowired
    public TaskConfig(RentService rentService, RoleRepository roleRepository, CarRepository carRepository) {
        this.rentService = rentService;
        this.roleRepository = roleRepository;
        this.carRepository = carRepository;
    }

    //"0 0/38 12-13 * * *" - every day at 12:38, 13:38

    /**
     * Function run every day to chane active fields in rented cars.
     * If car is rented and the return date is today, it'll change it
     */
    @Scheduled(cron = "0 0/30 1 * * *", zone = "Europe/Warsaw") //every day at 01:30
    public void scheduleTaskUsingCronExpression() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        cal.add(Calendar.DATE, 1); //added one day and it works

        Date d = cal.getTime();

        rentService.updateActiveOnReturnedByDate(d);

    }

    /**
     * Function run at start and check if role admin and user exist in database. If not
     * it 'll add it to db
     */
    @EventListener(ApplicationReadyEvent.class)
    public void doSomethingAfterStartup() {
        Optional<Role> roleUser = roleRepository.findByName(RoleName.ROLE_USER);

        Optional<Role> roleAdmin = roleRepository.findByName(RoleName.ROLE_ADMIN);
        Optional<Car> car = carRepository.findByVin("qwertyuiopasdfghj");
        if(roleUser.isEmpty()){
            Role role = new Role();
            role.setName(RoleName.ROLE_USER);
            roleRepository.save(role);
            System.out.println("user ROLE ADDED");
        }
        if(roleAdmin.isEmpty()){
            Role role = new Role();
            role.setName(RoleName.ROLE_ADMIN);
            roleRepository.save(role);
            System.out.println("ADMIN ROLE ADDED");
        }

        if(car.isEmpty()){
            Car car1 = new Car();
            car1.setActive(true);
            car1.setCarType(CarType.Asegment);
            car1.setDetails("details ");
            car1.setKmPassed(123124L);
            car1.setMark("BMW");
            car1.setProdDate(new Date());
            car1.setSeats(4);
            car1.setModel("e46");
            car1.setVin("qwertyuiopasdfghj");
            carRepository.save(car1);
            System.out.println("car added");
        }

    }
}
