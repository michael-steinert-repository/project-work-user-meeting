package de.share_your_idea.user_meeting.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.share_your_idea.user_meeting.entity.UserMeetingEntity;
import de.share_your_idea.user_meeting.entity.UserEntity;
import de.share_your_idea.user_meeting.entity.UserRole;
import de.share_your_idea.user_meeting.exception.CustomEmptyInputException;
import de.share_your_idea.user_meeting.exception.CustomNotFoundException;
import de.share_your_idea.user_meeting.http_client.UserManagementServiceHTTPClient;
import de.share_your_idea.user_meeting.repository.UserMeetingEntityRepository;
import de.share_your_idea.user_meeting.repository.UserEntityRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class UserMeetingService {
    private final UserEntityRepository userEntityRepository;
    private final UserMeetingEntityRepository userMeetingEntityRepository;
    private final UserManagementServiceHTTPClient userManagementServiceHttpClient;

    @Autowired
    public UserMeetingService(UserEntityRepository userEntityRepository,
                              UserMeetingEntityRepository userMeetingEntityRepository,
                              UserManagementServiceHTTPClient userManagementServiceHttpClient) {
        this.userEntityRepository = userEntityRepository;
        this.userMeetingEntityRepository = userMeetingEntityRepository;
        this.userManagementServiceHttpClient = userManagementServiceHttpClient;
    }

    public UserMeetingEntity saveMeeting(UserMeetingEntity userMeetingEntity) {
        log.info("User-Meeting-Service: SaveMeeting-Method is called");
        return userMeetingEntityRepository.save(userMeetingEntity);
    }

    public UserMeetingEntity saveNewMeeting(UserMeetingEntity userMeetingEntity, UserEntity userEntity) {
        log.info("User-Meeting-Service: SaveMeeting-Method is called");
        if(userMeetingEntity != null && userEntity != null) {
            /* Set UserEntity as Owner of the Meeting */
            userMeetingEntity.addUserEntity(userEntity);
            userEntity.setUserRole(UserRole.ROLE_OWNER);
            UserEntity userEntityFromRepository = userEntityRepository.save(userEntity);
            userManagementServiceHttpClient.saveAnExistingUserEntity(userEntityFromRepository);
            return userMeetingEntityRepository.save(userMeetingEntity);
        }
        throw new CustomNotFoundException(String.format("UserMeetingEntity with MeetingName %s or UserEntity with Username %s not found.",userMeetingEntity.getMeetingName(), userEntity.getUsername()));
    }

    public UserMeetingEntity findMeetingByMeetingName(String meetingName) {
        log.info("User-Meeting-Service: FindMeetingByMeetingName-Method is called");
        if (!meetingName.isBlank()) {
            Optional<UserMeetingEntity> userMeetingEntityOptional = userMeetingEntityRepository.findMeetingEntityByMeetingName(meetingName);
            UserMeetingEntity userMeetingEntity = userMeetingEntityOptional.get();
            return userMeetingEntity;
        }
        throw new CustomNotFoundException(String.format("UserMeetingEntity with MeetingName %s not found.",meetingName));
    }

    public List<UserMeetingEntity> findAllMeetings() {
        log.info("User-Meeting-Service: FindAllMeetings-Method is called");
        return userMeetingEntityRepository.findAll();
    }

    public UserMeetingEntity registerUserToMeeting(String meetingName, UserEntity userEntity) {
        log.info("User-Meeting-Service: RegisterUserToMeeting-Method is called");
        UserMeetingEntity userMeetingEntity = findMeetingByMeetingName(meetingName);
        if (userMeetingEntity != null && userEntity != null) {
            userEntity.setUserMeetingEntity(userMeetingEntity);
            UserEntity userEntityFromRepository = userEntityRepository.save(userEntity);
            userMeetingEntity.addUserEntity(userEntityFromRepository);
            return userMeetingEntityRepository.save(userMeetingEntity);
        }
        throw new CustomNotFoundException(String.format("UserMeetingEntity with MeetingName %s not found.",meetingName));
    }

    public UserMeetingEntity unregisterUserToMeeting(String meetingName, UserEntity userEntity) {
        log.info("User-Meeting-Service: RegisterUserToMeeting-Method is called");
        UserMeetingEntity userMeetingEntity = findMeetingByMeetingName(meetingName);
        if (userMeetingEntity != null && userEntity != null) {
            userEntity.setUserMeetingEntity(null);
            UserEntity userEntityFromRepository = userEntityRepository.save(userEntity);
            userMeetingEntity.removeUserEntity(userEntityFromRepository);
            return userMeetingEntityRepository.save(userMeetingEntity);
        }
        throw new CustomNotFoundException(String.format("UserMeetingEntity with MeetingName %s not found.",meetingName));
    }

    public int deleteMeetingByMeetingName(String meetingName, UserEntity userEntity) {
        log.info("User-Meeting-Service: DeleteMeetingByMeetingName-Method is called");
        if(!meetingName.isBlank() && userEntity != null && userEntity.getUserRole().name().equals(UserRole.ROLE_OWNER)) {
            userEntity.setUserMeetingEntity(null);
            userEntity.setUserRole(UserRole.ROLE_USER);
            userManagementServiceHttpClient.saveAnExistingUserEntity(userEntity);
            /* Delete all User from Meeting [if there are any] */
            Optional<UserMeetingEntity> userMeetingEntityOptional = userMeetingEntityRepository.findMeetingEntityByMeetingName(meetingName);
            if(userMeetingEntityOptional.isPresent()) {
                List<UserEntity> userEntityList = new ArrayList<>();
                UserMeetingEntity userMeetingEntity = userMeetingEntityOptional.get();
                List<UserEntity> userEntityFromRepositoryList = userEntityRepository.findUserEntitiesByUserMeetingEntity(userMeetingEntity);
                for(UserEntity userEntityFromRepository : userEntityFromRepositoryList) {
                    userEntityFromRepository.setUserMeetingEntity(null);
                    userEntityList.add(userEntityFromRepository);
                }
                userManagementServiceHttpClient.saveAllExistingUserEntities(userEntityList);
            }
            return userMeetingEntityRepository.deleteMeetingEntityByMeetingName(meetingName);
        }
        throw new CustomNotFoundException(String.format("UserMeetingEntity with MeetingName %s or UserEntity with Username %s not found.", meetingName, userEntity.getUsername()));
    }

    public UserEntity saveUser(UserEntity userEntity) {
        log.info("User-Meeting-Service: SaveUser-Method is called");
        return userEntityRepository.save(userEntity);
    }

    public UserEntity findUserByUsername(String username) throws JsonProcessingException, CustomNotFoundException {
        log.info("User-Meeting-Search-Service: FindUserByUsername-Method is called");
        if (!username.isBlank()) {
            ResponseEntity<UserEntity> responseEntity = userManagementServiceHttpClient.findUserEntityByUsername(username);
            if (responseEntity != null && responseEntity.hasBody()) {
                UserEntity userEntity = responseEntity.getBody();
                Optional<UserEntity> userEntityOptional = userEntityRepository.findUserEntityByUsername(userEntity.getUsername());
                UserEntity userEntityFromRepository = userEntityOptional.get();
                /*
                Server-side Caching:
                    Here the UserEntity is cached to ensure that even if the Microservice UserManagement is not available,
                    or the UserEntity is the same, the existing UserEntity is returned from the Repository.
                    This increases the Resilience and Performance of this Service.
                */
                if (!userEntity.equals(userEntityFromRepository)) {
                    int result = userEntityRepository.deleteUserEntityByUsername(userEntity.getUsername());
                    log.info("User-Meeting-Search-Service: FindUserByUsername-Method deleted UserEntity with Result : {}", new ObjectMapper().writeValueAsString(result));
                    if (result != 0) {
                        userEntity = userEntityRepository.save(userEntity);
                    }
                }
                return userEntity;
            }
            throw new CustomNotFoundException(String.format("UserEntity with Username %s not found.", username));
        }
        throw new CustomEmptyInputException("The Username is empty.");
    }

    public List<UserEntity> findAllUsers() {
        log.info("User-Meeting-Service: FindAllUsers-Method is called");
        return userEntityRepository.findAll();
    }
}
