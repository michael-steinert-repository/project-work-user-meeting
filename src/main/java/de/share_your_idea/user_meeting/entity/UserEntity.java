package de.share_your_idea.user_meeting.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Objects;

/*
Entity contains only the Properties specific to the Domain.
There is no Constraint for unique Username, because it is not needed in the Domain of User-Meeting.
The Target of the DDD is an Entity customized to their Domain.
*/

@Entity(name = "UserEntity")
@Table(name = "user_entity")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(
            name = "user_id",
            updatable = false
    )
    private Long userId;

    @Column(
            name = "username",
            nullable = false,
            columnDefinition = "TEXT"
    )
    @NotBlank(message = "Username must be not empty")
    private String username;

    @Column(
            name = "password",
            nullable = false,
            columnDefinition = "TEXT"
    )
    @NotBlank(message = "Password must be not empty")
    private String password;

    @Column(
            name = "user_role",
            nullable = false,
            columnDefinition = "TEXT"
    )
    @Enumerated(EnumType.STRING)
    private UserRole userRole;

    @Column(
            name = "authorization_token",
            nullable = false,
            columnDefinition = "TEXT"
    )
    @NotBlank(message = "AuthorizationToken must be not empty")
    private String authorizationToken;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(
            name = "meeting_id",
            referencedColumnName = "meeting_id",
            nullable = true,
            updatable = true
    )
    private UserMeetingEntity userMeetingEntity;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserEntity that = (UserEntity) o;
        return Objects.equals(username, that.username) && Objects.equals(password, that.password) && userRole == that.userRole && Objects.equals(authorizationToken, that.authorizationToken) && Objects.equals(userMeetingEntity, that.userMeetingEntity);
    }
}
