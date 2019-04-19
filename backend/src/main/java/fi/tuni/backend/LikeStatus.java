package fi.tuni.backend;

import javax.persistence.*;

@Entity
@IdClass(LikeStatusInfo.class)
public class LikeStatus {
    @Id
    private int authorId;

    @Id
    private int articleId;

    public int getAuthorId() {
        return authorId;
    }

    public void setAuthorId(int authorId) {
        this.authorId = authorId;
    }

    public int getArticleId() {
        return articleId;
    }

    public void setArticleId(int articleId) {
        this.articleId = articleId;
    }

    public LikeStatus(int authorId, int articleId) {
        this.authorId = authorId;
        this.articleId = articleId;
    }

    public LikeStatus() {}

    @Override
    public String toString() {
        return String.format("{Author: %d, Article: %d}", authorId, articleId);
    }
}
