package com.marcinrosol.carrental.configurations;

import com.marcinrosol.carrental.models.Rent;
import com.marcinrosol.carrental.repositories.RentRepository;
import com.marcinrosol.carrental.services.RentService;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import javax.transaction.Transactional;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Configuration
@EnableScheduling
public class TaskConfig {


    private RentService rentService;

    @Autowired
    public TaskConfig(RentService rentService) {
        this.rentService = rentService;
    }

    //"0 0/38 12-13 * * *" - every day at 12:38, 13:38
    @Scheduled(cron = "*/60 * * * * *", zone = "Europe/Warsaw")
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
}
