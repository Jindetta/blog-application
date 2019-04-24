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
    /**
     * Finds comments with given blog post id.
     *
     * @param articleId Id of blog post.
     * @return Comments found with blog post id.
     */
    Iterable<Comment> findByArticleId(int articleId);

    /**
     * Finds comment with comment id and article id.
     *
     * @param commentId Id of comment.
     * @param articleId Id of article.
     * @return Comment found with comment id and article id.
     */
    Optional<Comment> findByIdAndArticleId(int commentId, int articleId);

    /**
     * Finds comments find given author id.
     *
     * @param authorId Id of author.
     * @return Comments made by given author.
     */
    Iterable<Comment> findByAuthorId(int authorId);

    /**
     * Find comment with comment id and author id.
     *
     * @param commentId Id of comment.
     * @param authorId Id of author.
     * @return Comment found with comment id and author id.
     */
    Optional<Comment> findByIdAndAuthorId(int commentId, int authorId);
}
