package fi.tuni.backend;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.time.LocalDate;

/**
 * POJO for storing article information.
 *
 * @author Joonas Lauhala {@literal <joonas.lauhala@tuni.fi>}
 *         Tuukka Juusela {@literal <tuukka.juusela@tuni.fi}
 * @version 20192802
 * @since   1.8
 */
@Entity
public class Article implements HateoasInterface {

    /**
     * Stores identifier.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    /**
     * Stores creation date.
     */
    @Column
    private LocalDate date;

    /**
     * Stores title.
     */
    @Column
    private String title;

    /**
     * Stores content.
     */
    @Column(length = 10000)
    private String content;

    /**
     * Stores author information.
     */
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "author_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private User author;

    /**
     * Overrides default constructor.
     */
    public Article() {}

    /**
     * Overloads default constructor.
     *
     * @param title     Title value.
     * @param content   Content value.
     * @param author    Author instance.
     */
    public Article(String title, String content, User author) {
        this(LocalDate.now(), title, content, author);
    }

    /**
     * Overloads default constructor.
     *
     * @param date      Date value.
     * @param title     Title value.
     * @param content   Content value.
     * @param author    Author instance.
     */
    public Article(LocalDate date, String title, String content, User author) {
        setDate(date);
        setTitle(title);
        setContent(content);
        setAuthor(author);
    }

    /**
     * Overloads default constructor.
     *
     * @param id        Object identifier.
     * @param date      Date value.
     * @param title     Title value.
     * @param content   Content value.
     * @param author    Author instance.
     */
    public Article(int id, LocalDate date, String title, String content, User author) {
        setDate(date);
        setTitle(title);
        setContent(content);
        setAuthor(author);
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
     * Gets title.
     *
     * @return Title as String.
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets title.
     *
     * @param title Title value.
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Gets content.
     *
     * @return Content as String.
     */
    public String getContent() {
        return content;
    }

    /**
     * Sets content.
     *
     * @param content Content value.
     */
    public void setContent(String content) {
        this.content = content;
    }

    /**
     * Gets user information.
     *
     * @return User object.
     */
    public User getAuthor() {
        return author;
    }

    /**
     * Gets user identifier.
     *
     * @return User identifier as integer.
     */
    public int getAuthor_id() {
        return author.getId();
    }

    /**
     * Sets user information.
     *
     * @param author Author instance.
     */
    public void setAuthor(User author) {
        this.author = author;
    }

    /**
     * Gets creation date.
     *
     * @return Date object.
     */
    public LocalDate getDate() {
        return date;
    }

    /**
     * Sets date.
     *
     * @param date Date value.
     */
    public void setDate(LocalDate date) {
        this.date = date;
    }

    /**
     * Gets link to this object.
     *
     * @return Link as String.
     */
    @Override
    public String getLink() {
        return String.format("/blogs/%d", id);
    }

    /**
     * Overrides default toString().
     *
     * @return String representation of this object.
     */
    @Override
    public String toString() {
        return String.format("{Id: %d, AuthorId: %d, Title: \"%s\", Content: \"%s\"}", id, author.getId(), title, content);
    }

    /**
     * Overrides default equals().
     *
     * @param object    Object to check.
     * @return          True if object is equal, false otherwise.
     */
    @Override
    public boolean equals(Object object) {
        if (object instanceof Article) {
            Article articleData = (Article) object;

            return this == object ||
                   title.equals(articleData.getTitle()) &&
                   content.equals(articleData.getContent()) &&
                   author.equals(articleData.getAuthor()) &&
                   date.equals(articleData.getDate()) &&
                   id == articleData.getId();
        }

        return false;
    }
}
