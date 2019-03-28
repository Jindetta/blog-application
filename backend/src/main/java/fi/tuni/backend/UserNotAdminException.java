package fi.tuni.backend;

public class UserNotAdminException extends IllegalArgumentException {
    private String message;

    public UserNotAdminException(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
