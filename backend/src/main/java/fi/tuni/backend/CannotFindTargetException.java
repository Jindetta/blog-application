package fi.tuni.backend;

/**
 * Custom exception for non-existing target.
 *
 * @author Joonas Lauhala {@literal <joonas.lauhala@tuni.fi>}
 *         Tuukka Juusela {@literal <tuukka.juusela@tuni.fi}
 * @version 20192802
 * @since   1.8
 */
public class CannotFindTargetException extends IllegalArgumentException {

    /**
     * Stores target identifier.
     */
    private int targetID;

    /**
     * Stores exception message.
     */
    private String message;

    /**
     * Overrides default constructor.
     *
     * @param targetID  Target identifier.
     * @param message   Exception message.
     */
    public CannotFindTargetException(int targetID, String message) {
        this.targetID = targetID;
        this.message = message;
    }

    /**
     * Gets target identifier.
     *
     * @return Target identifier as integer.
     */
    public int getTargetID() {
        return targetID;
    }

    /**
     * Gets exception message.
     *
     * @return Exception message as String.
     */
    public String getMessage() {
        return message;
    }
}
