package fi.tuni.backend;

import javax.persistence.*;

/**
 * POJO for storing like status information.
 *
 * @author Joonas Lauhala {@literal <joonas.lauhala@tuni.fi>}
 *         Tuukka Juusela {@literal <tuukka.juusela@tuni.fi}
 * @version 20192802
 * @since   1.8
 */
@Entity
@IdClass(LikeStatusInfo.class)
public class LikeStatus {

    /**
     * Stores user identifier.
     */
    @Id
    private int likerId;

    /**
     * Stores comment identifier.
     */
    @Id
    private int commentId;

    /**
     * Gets user identifier.
     *
     * @return User identifier as integer.
     */
    public int getLikerId() {
        return likerId;
    }

    /**
     * Sets user identifier.
     *
     * @param likerId   User identifier.
     */
    public void setLikerId(int likerId) {
        this.likerId = likerId;
    }

    /**
     * Gets comment identfier.
     *
     * @return Comment identifier as integer.
     */
    public int getCommentId() {
        return commentId;
    }

    /**
     * Sets comment identifier.
     *
     * @param commentId Comment identifier.
     */
    public void setCommentId(int commentId) {
        this.commentId = commentId;
    }

    /**
     * Overrides default constructor.
     */
    public LikeStatus() {}

    /**
     * Overloads default constructor.
     *
     * @param likerId       User identifier.
     * @param commentId     Comment identifier.
     */
    public LikeStatus(int likerId, int commentId) {
        this.likerId = likerId;
        this.commentId = commentId;
    }

    /**
     * Overrides default toString().
     *
     * @return String representation of this object.
     */
    @Override
    public String toString() {
        return String.format("{LikerId: %d, CommentId: %d}", likerId, commentId);
    }
}
