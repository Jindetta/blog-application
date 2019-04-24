package fi.tuni.backend;

/**
 *
 */
public class Role {
    private RoleTypes role;
    private int userId;

    public Role(RoleTypes role, int userId) {
        setRole(role);
        setUserId(userId);
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getRole() {
        return role.toString();
    }

    public void setRole(RoleTypes role) {
        this.role = role;
    }

    public enum RoleTypes {
        ADMIN, USER, ANONYMOUS
    }
}
