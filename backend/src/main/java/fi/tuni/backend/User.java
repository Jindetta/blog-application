package fi.tuni.backend;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity
public class User implements HateoasInterface {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(unique = true)
    private String username;

    @Column(length = 60)
    @JsonIgnore
    private String password;

    @Column
    private boolean admin;

    public int getId() {
        return id;
    }

    public User(String username, String password) {
        setUsername(username);
        setPassword(password);
    }

    public User(String username, String password, boolean admin) {
        setUsername(username);
        setPassword(password);
        setAdmin(admin);
    }

    public boolean isAdmin() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }

    public User(){}

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String getLink() {
        return String.format("/users/%d", id);
    }

    @Override
    public String toString() {
        return String.format("{Id: %d, Admin: %b, username: %s, password: %s}", id, admin, username, password);
    }
}
