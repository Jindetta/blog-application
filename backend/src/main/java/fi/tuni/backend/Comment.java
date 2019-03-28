package fi.tuni.backend;

import javax.persistence.*;

@Entity
public class Comment {
    @Id
    @GeneratedValue
    private int id;

    @ManyToOne
    @JoinColumn
    private User author;

    @ManyToOne
    @JoinColumn
    private Article article;

    @Column(length = 1000)
    private String comment;

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

    public void setAuthor(User author) {
        this.author = author;
    }

    public Article getArticle() {
        return article;
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
}
