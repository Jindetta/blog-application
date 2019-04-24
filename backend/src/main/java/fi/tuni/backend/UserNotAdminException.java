package fi.tuni.backend;

/**
 * Custom exception class for non-admin users.
 *
 * @author Joonas Lauhala {@literal <joonas.lauhala@tuni.fi>}
 *         Tuukka Juusela {@literal <tuukka.juusela@tuni.fi}
 * @version 20192802
 * @since   1.8
 */
public class UserNotAdminException extends IllegalArgumentException {

    /**
     * Stores exception message.
     */
    private String message;

    /**
     * Overrides default constructor.
     *
     * @param message Exception message.
     */
    public UserNotAdminException(String message) {
        this.message = message;
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
