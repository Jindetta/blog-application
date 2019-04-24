package fi.tuni.backend;

public class CommentLikeResponse implements HateoasInterface {
    private Comment comment;
    private int likes;
    private boolean hasLiked;

    public CommentLikeResponse(Comment comment, int likes, boolean hasLiked) {
        this.comment = comment;
        this.likes = likes;
        this.hasLiked = hasLiked;
    }

    public CommentLikeResponse(){}

    public boolean isHasLiked() {
        return hasLiked;
    }

    public void setComment(Comment comment) {
        this.comment = comment;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public void setHasLiked(boolean hasLiked) {
        this.hasLiked = hasLiked;
    }

    public Comment getComment() {
        return comment;
    }

    public int getLikes() {
        return likes;
    }

    @Override
    public String getLink() {
        return String.format("/blogs/comments/likes/%d", comment.getId());
    }
}
