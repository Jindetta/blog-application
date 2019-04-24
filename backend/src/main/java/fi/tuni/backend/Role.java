package fi.tuni.backend;

/**
 * POJO for storing role information.
 *
 * @author Joonas Lauhala {@literal <joonas.lauhala@tuni.fi>}
 *         Tuukka Juusela {@literal <tuukka.juusela@tuni.fi}
 * @version 20192802
 * @since   1.8
 */
public class Role {

    /**
     * Stores user role type.
     */
    private RoleTypes role;

    /**
     * Stores user identifier.
     */
    private int userId;

    /**
     * Overrides default constructor.
     *
     * @param role      Role type.
     * @param userId    User identifier.
     */
    public Role(RoleTypes role, int userId) {
        setRole(role);
        setUserId(userId);
    }

    /**
     * Gets user identifier.
     *
     * @return User identifier as integer.
     */
    public int getUserId() {
        return userId;
    }

    /**
     * Sets user identifier.
     *
     * @param userId    User identifier.
     */
    public void setUserId(int userId) {
        this.userId = userId;
    }

    /**
     * Gets user role.
     *
     * @return User role as String.
     */
    public String getRole() {
        return role.toString();
    }

    /**
     * Sets user role type.
     *
     * @param role  Role type.
     */
    public void setRole(RoleTypes role) {
        this.role = role;
    }

    /**
     * Enumeration for role types.
     */
    public enum RoleTypes {

        /**
         * Admin user.
         */
        ADMIN,

        /**
         * Regular user.
         */
        USER,

        /**
         * Anonymous user.
         */
        ANONYMOUS
    }
}
