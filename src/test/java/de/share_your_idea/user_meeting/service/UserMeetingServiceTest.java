package de.share_your_idea.user_meeting.service;

import de.share_your_idea.user_meeting.entity.UserMeetingEntity;
import de.share_your_idea.user_meeting.http_client.UserManagementServiceHTTPClient;
import de.share_your_idea.user_meeting.repository.UserMeetingEntityRepository;
import de.share_your_idea.user_meeting.repository.UserEntityRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

class UserMeetingServiceTest {
    @Mock
    private UserEntityRepository userEntityRepository;
    @Mock
    private UserMeetingEntityRepository userMeetingEntityRepository;
    @Mock
    private UserManagementServiceHTTPClient userManagementServiceHTTPClient;
    @Captor
    private ArgumentCaptor<UserMeetingEntity> userMeetingEntityArgumentCaptor;
    @Captor
    private ArgumentCaptor<String> stringArgumentCaptor;
    private UserMeetingService userMeetingService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        userMeetingService = new UserMeetingService(userEntityRepository, userMeetingEntityRepository, userManagementServiceHTTPClient);
    }

    @Test
    void itShouldSaveMeeting() {
        /* Given */
        UserMeetingEntity userMeetingEntity = new UserMeetingEntity(1L, "testMeetingName", "testCommunicationLink", null);
        /* Mocking the Return if Method save() is called */
        given(userMeetingEntityRepository.save(any(UserMeetingEntity.class))).willReturn(any(UserMeetingEntity.class));
        /* When */
        UserMeetingEntity insertResult = userMeetingService.saveMeeting(userMeetingEntity);
        /* Then */
        /* The Method verify() verifies that the Method save() is invoked in the Repository */
        /* The Method capture() captures the Value in the Method save() */
        verify(userMeetingEntityRepository).save(userMeetingEntityArgumentCaptor.capture());
        UserMeetingEntity userMeetingEntityFromService = userMeetingEntityArgumentCaptor.getValue();
        assertThat(userMeetingEntityFromService.getMeetingId()).isEqualTo(userMeetingEntity.getMeetingId());
        assertThat(userMeetingEntityFromService.getMeetingName()).isEqualTo(userMeetingEntity.getMeetingName());
        assertThat(userMeetingEntityFromService.getCommunicationLink()).isEqualTo(userMeetingEntity.getCommunicationLink());
        assertThat(userMeetingEntityFromService.getUserEntityList()).isEqualTo(userMeetingEntity.getUserEntityList());
        assertThat(insertResult).isEqualTo(any(UserMeetingEntity.class));
    }

    @Test
    void itShouldFindMeetingByMeetingName() {
        /* Given */
        UserMeetingEntity userMeetingEntity = new UserMeetingEntity(1L, "testMeetingName", "testCommunicationLink", null);
        /* Mocking the Return if Method findMeetingEntityByMeetingName() is called */
        given(userMeetingEntityRepository.findMeetingEntityByMeetingName(any(String.class))).willReturn(Optional.of(userMeetingEntity));
        /* When */
        UserMeetingEntity userMeetingEntityFromService = userMeetingService.findMeetingByMeetingName(userMeetingEntity.getMeetingName());
        /* Then */
        /* The Method verify() verifies that the Method findMeetingEntityByMeetingName() is invoked in the Repository */
        /* The Method capture() captures the Value in the Method findMeetingEntityByMeetingName() */
        verify(userMeetingEntityRepository).findMeetingEntityByMeetingName(stringArgumentCaptor.capture());
        String userMeetingArgumentCaptorValue = stringArgumentCaptor.getValue();
        assertThat(userMeetingArgumentCaptorValue).isEqualTo(userMeetingEntity.getMeetingName());
        assertThat(userMeetingEntityFromService.getMeetingId()).isEqualTo(userMeetingEntity.getMeetingId());
        assertThat(userMeetingEntityFromService.getMeetingName()).isEqualTo(userMeetingEntity.getMeetingName());
        assertThat(userMeetingEntityFromService.getCommunicationLink()).isEqualTo(userMeetingEntity.getCommunicationLink());
        assertThat(userMeetingEntityFromService.getUserEntityList()).isEqualTo(userMeetingEntity.getUserEntityList());
    }
}