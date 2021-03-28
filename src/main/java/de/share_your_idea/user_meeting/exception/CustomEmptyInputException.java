package de.share_your_idea.user_meeting.exception;

public class CustomEmptyInputException extends RuntimeException {
    /* Unchecked Exceptions are checked at Runtime */
    public CustomEmptyInputException(String message) {
        super(message);
    }
}
