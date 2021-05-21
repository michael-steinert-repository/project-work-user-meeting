package de.share_your_idea.user_meeting;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableEurekaClient
@EnableFeignClients
@OpenAPIDefinition(info = @Info(title = "UserMeeting", version = "1.0", description = "Microservice for UserMeeting"))
public class UserMeetingApplication {
    public static void main(String[] args) {
        SpringApplication.run(UserMeetingApplication.class, args);
    }
}
