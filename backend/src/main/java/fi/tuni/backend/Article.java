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

    private LocalDate date;

    private String title;

    @Column(length = 10000)
    private String content;

    private int authorID;

    public Article(LocalDate date, String title, String content, int author) {
        this.date = date;
        this.title = title;
        this.content = content;
        this.authorID = author;
    }

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
        return authorID;
    }

    public void setAuthor(int author) {
        this.authorID = author;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
}
