package fi.tuni.backend;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

/**
 * POJO for storing user information.
 *
 * @author Joonas Lauhala {@literal <joonas.lauhala@tuni.fi>}
 *         Tuukka Juusela {@literal <tuukka.juusela@tuni.fi}
 * @version 20192802
 * @since   1.8
 */
@Entity
public class User implements HateoasInterface {

    /**
     * Stores identifier.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    /**
     * Stores username.
     */
    @Column(unique = true)
    private String username;

    /**
     * Stores password.
     */
    @Column(length = 60)
    @JsonIgnore
    private String password;

    /**
     * Stores admin state.
     */
    @Column
    private boolean admin;

    /**
     * Overrides default constructor.
     */
    public User(){}

    /**
     * Overloads default constructor.
     *
     * @param username  Username value.
     * @param password  Password value.
     */
    public User(String username, String password) {
        setUsername(username);
        setPassword(password);
    }

    /**
     * Overloads default constructor.
     *
     * @param username  Username value.
     * @param password  Password value.
     * @param admin     Admin state.
     */
    public User(String username, String password, boolean admin) {
        setUsername(username);
        setPassword(password);
        setAdmin(admin);
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
     * Checks if user is admin.
     *
     * @return True if user is admin, false otherwise.
     */
    public boolean isAdmin() {
        return admin;
    }

    /**
     * Sets user admin state.
     *
     * @param admin Admin state.
     */
    public void setAdmin(boolean admin) {
        this.admin = admin;
    }

    /**
     * Gets username.
     *
     * @return Username as String.
     */
    public String getUsername() {
        return username;
    }

    /**
     * Sets username.
     *
     * @param username  Username value.
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Gets password.
     *
     * @return Password as String.
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets password.
     *
     * @param password  Password value.
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Gets link to this object.
     *
     * @return Link as String.
     */
    @Override
    public String getLink() {
        return String.format("/users/%d", id);
    }

    /**
     * Overrides default toString().
     *
     * @return String representation of this object.
     */
    @Override
    public String toString() {
        return String.format("{Id: %d, Admin: %b, username: %s, password: %s}", id, admin, username, password);
    }

    /**
     * Overrides default constructor.
     *
     * @param object    Object to compare.
     * @return          True if object is equal, false otherwise.
     */
    @Override
    public boolean equals(Object object) {
        if (object instanceof User) {
            User userData = (User) object;

            return this == object ||
                   username.equals(userData.getUsername()) &&
                   password.equals(userData.getPassword()) &&
                   admin == userData.isAdmin() &&
                   id == userData.getId();
        }

        return false;
    }
}
