package de.share_your_idea.user_meeting;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableEurekaClient
@EnableFeignClients
public class UserMeetingApplication {
    public static void main(String[] args) {
        SpringApplication.run(UserMeetingApplication.class, args);
    }
}
