package com.thinkstep.test.onlineusers;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories("")
public class OnlineUsersApplication {

    public static void main(String[] args) {
        SpringApplication.run(OnlineUsersApplication.class, args);
    }
}
