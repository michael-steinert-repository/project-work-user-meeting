package de.share_your_idea.user_meeting.security;

import de.share_your_idea.user_meeting.entity.UserEntity;
import de.share_your_idea.user_meeting.service.UserMeetingService;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class CustomUserDetailsService implements UserDetailsService {
    private final UserMeetingService userMeetingService;

    @Autowired
    public CustomUserDetailsService(UserMeetingService userMeetingService) {
        this.userMeetingService = userMeetingService;
    }

    @SneakyThrows
    @Override
    public CustomUserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("CustomUserDetailsService: LoadUserByUsername Method is called");
        UserEntity userEntity = userMeetingService.findUserByUsername(username);
        return CustomUserDetails.fromUserEntityToCustomUserDetails(userEntity);
    }
}
