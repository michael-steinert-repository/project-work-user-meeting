package de.share_your_idea.user_meeting.repository;

import de.share_your_idea.user_meeting.entity.UserMeetingEntity;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.PropertySource;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

/* Unit-Test for MeetingEntityRepository */
/* To trigger the Annotations from UserEntity the following Property is necessary */
/* For Example @Column(nullable = false) */
@DataJpaTest(properties = "spring.jpa.properties.javax.persistence.validation.mode=none")
@PropertySource("classpath:application.yml")
@PropertySource("classpath:bootstrap.yml")
class UserMeetingEntityRepositoryTest {
    @Autowired
    UserMeetingEntityRepository userMeetingEntityRepository;

    @AfterEach
    void tearDown() {
        userMeetingEntityRepository.deleteAll();
    }

    @Test
    void itShouldFindMeetingEntityByMeetingName() {
        /* Given */
        UserMeetingEntity userMeetingEntity = new UserMeetingEntity(1L, "testMeetingName", "testCommunicationLink", null);
        /* When */
        userMeetingEntityRepository.save(userMeetingEntity);
        /* Then */
        Optional<UserMeetingEntity> userMeetingEntityOptional = userMeetingEntityRepository.findMeetingEntityByMeetingName(userMeetingEntity.getMeetingName());
        assertThat(userMeetingEntityOptional).isPresent().hasValueSatisfying(userMeetingEntityFromRepository -> {
            assertThat(userMeetingEntityFromRepository.getMeetingName()).isEqualTo(userMeetingEntity.getMeetingName());
            assertThat(userMeetingEntityFromRepository.getCommunicationLink()).isEqualTo(userMeetingEntity.getCommunicationLink());
            assertThat(userMeetingEntityFromRepository.getUserEntityList()).isEqualTo(userMeetingEntity.getUserEntityList());
        });
    }

    @Test
    void itShouldDeleteMeetingEntityByMeetingName() {
        /* Given */
        UserMeetingEntity userMeetingEntity = new UserMeetingEntity(1L, "testMeetingName", "testCommunicationLink", null);
        /* When */
        userMeetingEntityRepository.save(userMeetingEntity);
        /* Then */
        int result = userMeetingEntityRepository.deleteMeetingEntityByMeetingName(userMeetingEntity.getMeetingName());
        assertThat(result).isEqualTo(1);
        Optional<UserMeetingEntity> userMeetingEntityOptional = userMeetingEntityRepository.findById(userMeetingEntity.getMeetingId());
        assertThat(userMeetingEntityOptional).isEmpty();
    }
}