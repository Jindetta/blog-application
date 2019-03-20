package fi.tuni.backend;

public class CannotFindTargetException extends IllegalArgumentException {
    private int targetID;
    private String message;

    public CannotFindTargetException(int targetID, String message) {
        this.targetID = targetID;
        this.message = message;
    }

    public int getTargetID() {
        return targetID;
    }

    public String getMessage() {
        return message;
    }
}
