package fi.tuni.backend;

/**
 *
 */
public class Permits {
    private PermitTypes permit;

    public Permits(PermitTypes permit) {
        setPermit(permit);
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
