package fi.tuni.backend;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Set;

@Entity
public class Article {
    @Id
    @GeneratedValue
    private int id;

    @Column
    private LocalDate date;

    @Column
    private String title;

    @Column(length = 10000)
    private String content;

    @ManyToOne
    @JoinColumn
    private User author;

    @OneToMany(mappedBy = "article", cascade = CascadeType.ALL)
    private Set<Comment> comments;

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
    public String toString() {
        return String.format("{Id: %d, AuthorId: %d, Title: \"%s\", Content: \"%s\"}", id, author.getId(), title, content);
    }
}
