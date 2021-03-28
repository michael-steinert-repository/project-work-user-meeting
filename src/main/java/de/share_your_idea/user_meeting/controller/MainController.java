package de.share_your_idea.user_meeting.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.share_your_idea.user_meeting.entity.UserEntity;
import de.share_your_idea.user_meeting.entity.UserMeetingEntity;
import de.share_your_idea.user_meeting.service.UserMeetingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@Controller
public class MainController {

    @Value("${spring.application.name}")
    private String serviceName;
    private UserMeetingService userMeetingService;

    @Autowired
    public MainController(UserMeetingService userMeetingService) {
        this.userMeetingService = userMeetingService;
    }

    @GetMapping(value = {"/", "/index"})
    public String showIndex(Model model) throws JsonProcessingException {
        log.info("Main-Controller: Index-Method is called");
        model.addAttribute("serviceName", serviceName);
        log.info("Main-Controller: Index-Method created ServiceName : {}", new ObjectMapper().writeValueAsString(serviceName));
        return "index";
    }

    @GetMapping(value = {"/userList"})
    public String showUserList(Model model) throws JsonProcessingException {
        log.info("Main-Controller: ShowUserList-Method is called");
        List<UserEntity> userEntityList = userMeetingService.findAllUsers();
        model.addAttribute("userEntityList", userEntityList);
        log.info("Main-Controller: ShowUserList-Method created UserEntityList : {}", new ObjectMapper().writeValueAsString(userEntityList));
        return "userList";
    }

    @GetMapping(value = {"/addUser"})
    public String showAddUser(Model model) {
        log.info("Main-Controller: ShowAddUserPage-Method is called");
        UserEntity userEntity = new UserEntity();
        model.addAttribute("userEntity", userEntity);
        return "addUser";
    }

    @PostMapping(value = {"/addUser"})
    public String showAddUser(@ModelAttribute("userEntity") UserEntity userEntity) throws JsonProcessingException {
        log.info("Main-Controller: AddUser-Method is called");
        userMeetingService.saveUser(userEntity);
        log.info("Main-Controller: AddUser-Method created and saved UserEntity : {}", new ObjectMapper().writeValueAsString(userEntity));
        return "addUser";
    }

    @GetMapping(value = {"/userMeetingList"})
    public String showUserMeetingList(Model model) throws JsonProcessingException {
        log.info("Main-Controller: ShowUserMeetingList-Method is called");
        List<UserMeetingEntity> userMeetingEntityList = userMeetingService.findAllMeetings();
        model.addAttribute("userMeetingEntityList", userMeetingEntityList);
        log.info("Main-Controller: ShowUserMeetingList-Method created UserMeetingEntityList : {}", new ObjectMapper().writeValueAsString(userMeetingEntityList));
        return "userMeetingList";
    }

    @GetMapping(value = {"/addUserMeeting"})
    public String showAddUserMeeting(Model model) {
        log.info("Main-Controller: ShowAddUserMeeting-Method is called");
        UserMeetingEntity userMeetingEntity = new UserMeetingEntity();
        model.addAttribute("userMeetingEntity", userMeetingEntity);
        return "addUserMeeting";
    }

    @PostMapping(value = {"/addUserMeeting"})
    public String showAddUserMeeting(@ModelAttribute("userMeetingEntity") UserMeetingEntity userMeetingEntity) throws JsonProcessingException {
        log.info("Main-Controller: ShowAddUserMeeting-Method is called");
        userMeetingService.saveNewMeeting(userMeetingEntity, null);
        log.info("Main-Controller: ShowAddUserMeeting-Method created and saved UserMeetingEntity : {}", new ObjectMapper().writeValueAsString(userMeetingEntity));
        return "addUserMeeting";
    }

    @ExceptionHandler(Exception.class)
    public String handleException(Model model, Exception exception) throws JsonProcessingException {
        model.addAttribute("exception", exception);
        log.info("Main-Controller: HandleException-Method threw Exception : {}", new ObjectMapper().writeValueAsString(exception));
        return "handleException";
    }
}