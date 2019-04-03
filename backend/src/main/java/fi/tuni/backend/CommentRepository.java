package fi.tuni.backend;

import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

/**
 * Interface for comment management.
 *
 * @author Joonas Lauhala {@literal <joonas.lauhala@tuni.fi>}
 *         Tuukka Juusela {@literal <tuukka.juusela@tuni.fi}
 * @version 20192802
 * @since   1.8
 */
public interface CommentRepository extends CrudRepository<Comment, Integer> {
    Iterable<Comment> findByArticleId(int articleId);
    Optional<Comment> findByIdAndArticleId(int commentId, int articleId);

    Iterable<Comment> findByAuthorId(int authorId);
    Optional<Comment> findByIdAndAuthorId(int commentId, int authorId);
}
