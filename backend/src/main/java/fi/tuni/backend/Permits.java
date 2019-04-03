package fi.tuni.backend;

public class Permits {
    public final static String ADMIN = "ADMIN";
    public final static String USER = "USER";
    public final static String ANONYMOUS= "ANONYMOUS";
    private String permit;

    public Permits(String permit) {
        setPermit(permit);
    }

    public String getPermit() {
        return permit;
    }

    public void setPermit(String permit) {
        this.permit = permit;
    }
}
