package fi.tuni.backend;
import java.io.Serializable;

/**
 * POJO for storing like status information.
 *
 * @author Joonas Lauhala {@literal <joonas.lauhala@tuni.fi>}
 *         Tuukka Juusela {@literal <tuukka.juusela@tuni.fi}
 * @version 20192802
 * @since   1.8
 */
public class LikeStatusInfo implements Serializable {

    /**
     * Stores user identifier.
     */
    public int likerId;

    /**
     * Stores comment identifier.
     */
    public int commentId;

    /**
     * Overrides default constructor.
     */
    public LikeStatusInfo(){}
}