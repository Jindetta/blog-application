package fi.tuni.backend;

/**
 * POJO for giving comment and comment like data in same response.
 *
 * @author Joonas Lauhala {@literal <joonas.lauhala@tuni.fi>}
 *         Tuukka Juusela {@literal <tuukka.juusela@tuni.fi}
 * @version 20192802
 * @since   1.8
 */
public class CommentLikeResponse implements HateoasInterface {

    /**
     * Data of comment.
     */
    private Comment comment;

    /**
     * Amount of likes comment has.
     */
    private int likes;

    /**
     * If current user has liked the comment.
     */
    private boolean hasLiked;

    /**
     * Sets parameters to variables.
     * @param comment Comment data.
     * @param likes Amount of likes.
     * @param hasLiked If user has liked the comment.
     */
    public CommentLikeResponse(Comment comment, int likes, boolean hasLiked) {
        this.comment = comment;
        this.likes = likes;
        this.hasLiked = hasLiked;
    }

    /**
     * Overrides default constructor.
     */
    public CommentLikeResponse(){}

    /**
     * Returns hasLiked variable.
     *
     * @return
     */
    public boolean isHasLiked() {
        return hasLiked;
    }

    /**
     * Sets comment to variable.
     *
     * @param comment Comment to set.
     */
    public void setComment(Comment comment) {
        this.comment = comment;
    }

    /**
     * Sets likes.
     *
     * @param likes Amount of likes.
     */
    public void setLikes(int likes) {
        this.likes = likes;
    }

    /**
     * Sets hasLiked.
     *
     * @param hasLiked If user has liked the comment.
     */
    public void setHasLiked(boolean hasLiked) {
        this.hasLiked = hasLiked;
    }

    /**
     * Returns comment data.
     * @return comment of POJO.
     */
    public Comment getComment() {
        return comment;
    }

    /**
     * Return amout of likes.
     * @return amount of likes.
     */
    public int getLikes() {
        return likes;
    }

    /**
     * Gets link to this object.
     * @return Link to access resource.
     */
    @Override
    public String getLink() {
        return String.format("/blogs/comments/likes/%d", comment.getId());
    }
}
