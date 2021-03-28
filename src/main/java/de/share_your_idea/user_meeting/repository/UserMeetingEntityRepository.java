package de.share_your_idea.user_meeting.repository;

import de.share_your_idea.user_meeting.entity.UserMeetingEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
@Transactional(readOnly = true)
public interface UserMeetingEntityRepository extends JpaRepository<UserMeetingEntity, Long> {
    Optional<UserMeetingEntity> findMeetingEntityByMeetingName(String meetingName);

    @Modifying
    @Transactional(readOnly = false)
    int deleteMeetingEntityByMeetingName(String meetingName);
}
