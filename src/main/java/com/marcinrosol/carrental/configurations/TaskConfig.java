package com.marcinrosol.carrental.configurations;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.concurrent.TimeUnit;

@Configuration
@EnableScheduling
public class TaskConfig {

    //"0 0/38 12-13 * * *" - every day at 12:38, 13:38
    @Scheduled(cron = "*/60 * * * * *", zone = "Europe/Warsaw")
    public void scheduleTaskUsingCronExpression() throws InterruptedException {
        String query = "thread immutable";


        long now = System.currentTimeMillis() / 1000;
        System.out.println(
                "schedule tasks using cron jobs - " + now);
    }
}
