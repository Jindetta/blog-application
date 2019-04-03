package fi.tuni.backend;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

/**
 * POJO for storing comment information.
 *
 * @author Joonas Lauhala {@literal <joonas.lauhala@tuni.fi>}
 *         Tuukka Juusela {@literal <tuukka.juusela@tuni.fi}
 * @version 20192802
 * @since   1.8
 */
@Entity
public class Comment implements HateoasInterface {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "author_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private User author;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "article_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private Article article;

    @Column(length = 1000)
    private String comment;

    public Comment() {
    }

    public Comment(User author, Article article, String comment) {
        setAuthor(author);
        setArticle(article);
        setComment(comment);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getAuthor() {
        return author;
    }

    public int getAuthor_id() {
        return author.getId();
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public Article getArticle() {
        return article;
    }

    public int getArticle_id() {
        return article.getId();
    }

    public void setArticle(Article article) {
        this.article = article;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    @Override
    public String getLink() {
        return String.format("/blogs/comments/%d", id);
    }

    @Override
    public String toString() {
        return String.format("{Author: %d, Article: %d, Comment: \"%s\"}", author.getId(), article.getId(), comment);
    }

    @Override
    public boolean equals(Object object) {
        if (object instanceof Comment) {
            Comment commentData = (Comment) object;

            return this == object ||
                   comment.equals(commentData.getComment()) &&
                   article.equals(commentData.getArticle()) &&
                   author.equals(commentData.getAuthor()) &&
                   id == commentData.getId();
        }

        return false;
    }
}
