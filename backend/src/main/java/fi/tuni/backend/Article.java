package fi.tuni.backend;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDate;

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

    @Column
    private int authorId;

    public Article(String title, String content, int authorId) {
        this(LocalDate.now(), title, content, authorId);
    }

    public Article(LocalDate date, String title, String content, int author) {
        setDate(date);
        setTitle(title);
        setContent(content);
        setAuthor(author);
    }

    public Article(int id, LocalDate date, String title, String content, int author) {
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

    public int getAuthor() {
        return authorId;
    }

    public void setAuthor(int author) {
        this.authorId = author;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "{AuthorID: " + authorId + " Date: " + date +  " Title: " + title + " Content: " + content + "}";
    }
}
