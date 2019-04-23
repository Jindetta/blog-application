package fi.tuni.backend;

import javax.persistence.*;

@Entity
@IdClass(LikeStatusInfo.class)
public class LikeStatus {
    @Id
    private int likerId;

    @Id
    private int commentId;

    public int getLikerId() {
        return likerId;
    }

    public void setLikerId(int likerId) {
        this.likerId = likerId;
    }

    public int getCommentId() {
        return commentId;
    }

    public void setCommentId(int commentId) {
        this.commentId = commentId;
    }

    public LikeStatus(int authorId, int articleId) {
        this.likerId = authorId;
        this.commentId = articleId;
    }

    public LikeStatus() {}

    @Override
    public String toString() {
        return String.format("{LikerId: %d, CommentId: %d}", likerId, commentId);
    }
}
