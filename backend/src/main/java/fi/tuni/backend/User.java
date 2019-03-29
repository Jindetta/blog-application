package fi.tuni.backend;

import javax.persistence.*;

@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column
    private String firstName;

    @Column
    private String lastName;

    @Column
    private boolean admin;

    public int getId() {
        return id;
    }

    public User(String firstName, String lastName) {
        setFirstName(firstName);
        setLastName(lastName);
    }

    public User(String firstName, String lastName, boolean admin) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.admin = admin;
    }

    public boolean isAdmin() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }

    public User(){}

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Override
    public String toString() {
        return String.format("{Id: %d, Admin: %b, Firstname: %s, Lastname: %s}", id, admin, firstName, lastName);
    }
}
