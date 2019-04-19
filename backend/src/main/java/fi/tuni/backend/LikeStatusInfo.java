package fi.tuni.backend;
import java.io.Serializable;

public class LikeStatusInfo implements Serializable {
    public int authorId;
    public int articleId;

    public LikeStatusInfo(){}
}