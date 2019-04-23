package fi.tuni.backend;
import java.io.Serializable;

public class LikeStatusInfo implements Serializable {
    public int likerId;
    public int commentId;

    public LikeStatusInfo(){}
}