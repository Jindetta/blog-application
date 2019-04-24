package fi.tuni.backend;

import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

/**
 * Interface for like management.
 *
 * @author Joonas Lauhala {@literal <joonas.lauhala@tuni.fi>}
 *         Tuukka Juusela {@literal <tuukka.juusela@tuni.fi}
 * @version 20192802
 * @since   1.8
 */
public interface LikeRepository extends CrudRepository<LikeStatus, Integer> {

    /***
     * Finds like with given liker id and comment id.
     *
     * @param likerId Id of liker.
     * @param CommentId Id of comment.
     * @return Optional with like if found. If not found returns Optional with null.
     */
    Optional<LikeStatus> findByLikerIdAndCommentId(int likerId, int CommentId);

    /**
     * Finds list of likes by comment id.
     *
     * @param id Id of comment.
     * @return List of comments found with id of comment.
     */
    List<LikeStatus> findLikeStatusesByCommentId(int id);
}
