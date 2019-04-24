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

    /**
     * Stores identifier.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    /**
     * Stores author information.
     */
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "author_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private User author;

    /**
     * Stores article information.
     */
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "article_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private Article article;

    /**
     * Stores comment information.
     */
    @Column(length = 1000)
    private String comment;

    /**
     * Overrides default constructor.
     */
    public Comment() {
    }

    /**
     * Overloads default constructor.
     *
     * @param author    Author instance.
     * @param article   Article instance.
     * @param comment   Comment instnace.
     */
    public Comment(User author, Article article, String comment) {
        setAuthor(author);
        setArticle(article);
        setComment(comment);
    }

    /**
     * Gets identifier.
     *
     * @return Identifier as integer.
     */
    public int getId() {
        return id;
    }

    /**
     * Sets identifier.
     *
     * @param id    Identifier.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Gets author information.
     *
     * @return Author object.
     */
    public User getAuthor() {
        return author;
    }

    /**
     * Gets author identifier.
     *
     * @return Author identifier as integer.
     */
    public int getAuthor_id() {
        return author.getId();
    }

    /**
     * Sets author information.
     *
     * @param author    Author instance.
     */
    public void setAuthor(User author) {
        this.author = author;
    }

    /**
     * Gets author information.
     *
     * @return Author object.
     */
    public Article getArticle() {
        return article;
    }

    /**
     * Gets article identifier.
     *
     * @return Article identifier as integer.
     */
    public int getArticle_id() {
        return article.getId();
    }

    /**
     * Sets article information.
     *
     * @param article   Article instance.
     */
    public void setArticle(Article article) {
        this.article = article;
    }

    /**
     * Gets comment information.
     *
     * @return Comment object.
     */
    public String getComment() {
        return comment;
    }

    /**
     * Sets comment information.
     *
     * @param comment   Comment instance.
     */
    public void setComment(String comment) {
        this.comment = comment;
    }

    /**
     * Gets link to this object.
     *
     * @return Link as String.
     */
    @Override
    public String getLink() {
        return String.format("/blogs/comments/%d", id);
    }

    /**
     * Overrides default toString().
     *
     * @return String representation of this object.
     */
    @Override
    public String toString() {
        return String.format("{Author: %d, Article: %d, Comment: \"%s\"}", author.getId(), article.getId(), comment);
    }

    /**
     * Overrides default equals().
     *
     * @param object    Object to compare.
     * @return          True if objects are equal, false otherwise.
     */
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
