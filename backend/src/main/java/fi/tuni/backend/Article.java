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
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column
    private LocalDate date;

    @Column
    private String title;

    @Column(length = 10000)
    private String content;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "author_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private User author;

    public Article(String title, String content, User author) {
        this(LocalDate.now(), title, content, author);
    }

    public Article(LocalDate date, String title, String content, User author) {
        setDate(date);
        setTitle(title);
        setContent(content);
        setAuthor(author);
    }

    public Article(int id, LocalDate date, String title, String content, User author) {
        setDate(date);
        setTitle(title);
        setContent(content);
        setAuthor(author);
    }

    public Article() {}

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
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

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    @Override
    public String getLink() {
        return String.format("/blogs/%d", id);
    }

    @Override
    public String toString() {
        return String.format("{Id: %d, AuthorId: %d, Title: \"%s\", Content: \"%s\"}", id, author.getId(), title, content);
    }
}
