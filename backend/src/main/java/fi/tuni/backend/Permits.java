package fi.tuni.backend;

/**
 *
 */
public class Permits {
    private PermitTypes permit;
    private int userId;

    public Permits(PermitTypes permit, int userId) {
        setPermit(permit);
        setUserId(userId);
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getPermit() {
        return permit.toString();
    }

    public void setPermit(PermitTypes permit) {
        this.permit = permit;
    }

    public enum PermitTypes {
        ADMIN, USER, ANONYMOUS
    }
}
