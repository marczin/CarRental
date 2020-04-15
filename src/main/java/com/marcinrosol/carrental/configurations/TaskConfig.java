package com.marcinrosol.carrental.configurations;

import com.marcinrosol.carrental.models.Enums.RoleName;
import com.marcinrosol.carrental.models.Rent;
import com.marcinrosol.carrental.models.Role;
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

    @Autowired
    public TaskConfig(RentService rentService, RoleRepository roleRepository) {
        this.rentService = rentService;
        this.roleRepository = roleRepository;
    }

    //"0 0/38 12-13 * * *" - every day at 12:38, 13:38
    @Scheduled(cron = "0 0/30 1 * * *", zone = "Europe/Warsaw") //every day at 01:30
    public void scheduleTaskUsingCronExpression() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        cal.add(Calendar.DATE, 1); //added one day and it works (infinity, day+1)

        Date d = cal.getTime();

        rentService.updateActiveOnReturnedByDate(d);

    }

    @EventListener(ApplicationReadyEvent.class)
    public void doSomethingAfterStartup() {
        Optional<Role> roleUser = roleRepository.findByName(RoleName.ROLE_USER);

        Optional<Role> roleAdmin = roleRepository.findByName(RoleName.ROLE_ADMIN);
        if(!roleUser.isPresent()){
            Role role = new Role();
            role.setName(RoleName.ROLE_USER);
            roleRepository.save(role);
            System.out.println("user ROLE ADDED");
        }
        if(!roleAdmin.isPresent()){
            Role role = new Role();
            role.setName(RoleName.ROLE_ADMIN);
            roleRepository.save(role);
            System.out.println("ADMIN ROLE ADDED");
        }

    }
}
