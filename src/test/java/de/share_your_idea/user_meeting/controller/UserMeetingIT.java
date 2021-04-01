package de.share_your_idea.user_meeting.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.share_your_idea.user_meeting.entity.UserMeetingEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/* Integration-Test for UserMeetingController */
@AutoConfigureMockMvc
@SpringBootTest
@PropertySource("classpath:application-it.yml")
@PropertySource("classpath:bootstrap-it.yml")
public class UserMeetingIT {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void itShouldSaveNewUserMeetingEntity() throws Exception {
        /* Given */
        UserMeetingEntity userMeetingEntity = new UserMeetingEntity(1L, "testMeetingName", "testCommunicationLink", null);
        /* When */
        /* Perform a PostRequest to the UserMeetingController to register a UserMeeting */
        ResultActions userPostResultActions = mockMvc.perform(post("/api/v1/user-meeting/register-meeting")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userMeetingEntity)));
        /* Perform a GetRequest to the UserMeetingController to get all UserMeetings */
        ResultActions userGetResultActions = mockMvc.perform(get("/api/v1/user-meeting/find-all-user-meetings"));
        /* Then */
        userPostResultActions.andExpect(status().isOk());
        /* Check if UserMeeting is stored in Repository */
        userGetResultActions
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(objectMapper.writeValueAsString(List.of(userMeetingEntity))));
    }
}
