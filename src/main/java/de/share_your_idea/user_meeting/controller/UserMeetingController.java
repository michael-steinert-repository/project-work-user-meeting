package de.share_your_idea.user_meeting.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.share_your_idea.user_meeting.entity.UserMeetingEntity;
import de.share_your_idea.user_meeting.entity.UserEntity;
import de.share_your_idea.user_meeting.service.UserMeetingService;
import javassist.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RequestMapping("api/v1/user-meeting")
@RestController
public class UserMeetingController {

    private final UserMeetingService userMeetingService;

    @Autowired
    public UserMeetingController(UserMeetingService userMeetingService) {
        this.userMeetingService = userMeetingService;
    }

    @PostMapping("/register-meeting")
    public ResponseEntity<UserMeetingEntity> registerMeeting(@RequestBody @Valid UserMeetingEntity userMeetingEntity, @RequestBody @Valid UserEntity userEntity) throws JsonProcessingException {
        log.info("Meeting-Controller: RegisterMeeting-Method is called");
        UserMeetingEntity savedUserMeetingEntity = userMeetingService.saveNewMeeting(userMeetingEntity, userEntity);
        log.info("Meeting-Controller: RegisterMeeting-Method saved MeetingEntity : {}", new ObjectMapper().writeValueAsString(savedUserMeetingEntity));
        return new ResponseEntity<>(savedUserMeetingEntity, HttpStatus.OK);
    }

    @GetMapping("/unregister-meeting/{meetingName}")
    public ResponseEntity<Integer> unregisterMeeting(@PathVariable("meetingName") String meetingName,  @RequestBody @Valid UserEntity userEntity) throws JsonProcessingException {
        log.info("Meeting-Controller: UnregisterMeeting-Method is called");
        int deleteResult = userMeetingService.deleteMeetingByMeetingName(meetingName, userEntity);
        log.info("Meeting-Controller: UnregisterMeeting-Method deleted MeetingEntity with Result : {}", new ObjectMapper().writeValueAsString(deleteResult));
        return new ResponseEntity<>(deleteResult, HttpStatus.OK);
    }

    @PostMapping("/register-to-meeting/{meetingName}")
    public ResponseEntity<UserMeetingEntity> registerUserToMeeting(@PathVariable("meetingName") String meetingName, @RequestBody @Valid UserEntity userEntity) throws JsonProcessingException {
        log.info("Meeting-Controller: RegisterUserToMeeting-Method is called");
        UserMeetingEntity userMeetingEntity = userMeetingService.registerUserToMeeting(meetingName, userEntity);
        log.info("Meeting-Controller: RegisterUserToMeeting-Method saved MeetingEntity : {}", new ObjectMapper().writeValueAsString(userMeetingEntity));
        return new ResponseEntity<>(userMeetingEntity, HttpStatus.OK);
    }

    @PostMapping("/unregister-from-meeting/{meetingName}")
    public ResponseEntity<UserMeetingEntity> unregisterUserFromMeeting(@PathVariable("meetingName") String meetingName, @RequestBody @Valid UserEntity userEntity) throws JsonProcessingException {
        log.info("Meeting-Controller: UnregisterUserFromMeeting-Method is called");
        UserMeetingEntity userMeetingEntity = userMeetingService.unregisterUserToMeeting(meetingName, userEntity);
        log.info("Meeting-Controller: UnregisterUserToMeeting-Method saved MeetingEntity : {}", new ObjectMapper().writeValueAsString(userMeetingEntity));
        return new ResponseEntity<>(userMeetingEntity, HttpStatus.OK);
    }

    @GetMapping(path = {"/find-all-user-meetings"})
    public ResponseEntity<List<UserMeetingEntity>> findAllUserMeetingEntities() throws JsonProcessingException {
        log.info("Meeting-Controller: FindAllUserMeetingEntities-Method is called");
        List<UserMeetingEntity> userMeetingEntityList = userMeetingService.findAllMeetings();
        log.info("Meeting-Controller: FindAllUserMeetingEntities-Method created UserMeetingEntityList : {}", new ObjectMapper().writeValueAsString(userMeetingEntityList));
        return new ResponseEntity<>(userMeetingEntityList, HttpStatus.OK);
    }

    @GetMapping(path = "/find-user-by-username/{username}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserEntity> findUserByUsername(@PathVariable("username") String username) throws JsonProcessingException, NotFoundException {
        log.info("Search-Query-Controller: FindUserByUsername-Method is called");
        UserEntity userEntity = userMeetingService.findUserByUsername(username);
        log.info("Search-Query-Controller: FindUserByUsername-Method created UserEntity : {}", new ObjectMapper().writeValueAsString(userEntity));
        return new ResponseEntity<>(userEntity, HttpStatus.OK);
    }
}