package de.share_your_idea.user_meeting.repository;

import de.share_your_idea.user_meeting.entity.UserEntity;
import de.share_your_idea.user_meeting.entity.UserMeetingEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional(readOnly = true)
public interface UserEntityRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findUserEntityByUsername(String username);

    List<UserEntity> findUserEntitiesByUserMeetingEntity(UserMeetingEntity userMeetingEntity);

    @Modifying
    @Transactional(readOnly = false)
    int deleteUserEntityByUsername(String username);
}
