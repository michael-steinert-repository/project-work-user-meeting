package de.share_your_idea.user_meeting.exception;

public class CustomNotFoundException extends RuntimeException {
    /* Unchecked Exceptions are checked at Runtime */
    public CustomNotFoundException(String message) {
        super(message);
    }
}
