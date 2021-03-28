package de.share_your_idea.user_meeting.http_client;

import de.share_your_idea.user_meeting.entity.UserEntity;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
import java.util.List;

@FeignClient("http://USER-MANAGEMENT-SERVICE/api/v1/user-management/")
public interface UserManagementServiceHTTPClient {

    @GetMapping("find-user-by-username/{username}")
    ResponseEntity<UserEntity> findUserEntityByUsername(@PathVariable("username") String username);

    @PostMapping("update-an-existing-user")
    ResponseEntity<UserEntity> saveAnExistingUserEntity(@RequestBody @Valid UserEntity userEntity);

    @PostMapping("update-all-existing-users")
    ResponseEntity<List<UserEntity>> saveAllExistingUserEntities(@RequestBody List<UserEntity> userEntityList);
}