package fi.tuni.backend;

public class CannotFindUserException  extends IllegalArgumentException {
    private int userID;
    private String message;

    public CannotFindUserException(int userID, String message) {
        this.userID = userID;
        this.message = message;
    }

    public int getUserID() {
        return userID;
    }

    public String getMessage() {
        return message;
    }
}
