package fi.tuni.backend;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.List;

public class CommentLikeResponse {
    private Comment comment;
    private List<LikeStatus> likes;
    private boolean hasLiked;

    public CommentLikeResponse(Comment comment, List<LikeStatus> likes, boolean hasLiked) {
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

    public void setLikes(List<LikeStatus> likes) {
        this.likes = likes;
    }

    public void setHasLiked(boolean hasLiked) {
        this.hasLiked = hasLiked;
    }

    public Comment getComment() {
        return comment;
    }

    public List<LikeStatus> getLikes() {
        return likes;
    }
}
