package fi.tuni.backend;

import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface CommentRepository extends CrudRepository<Comment, Integer> {
    Iterable<Comment> findByArticleId(int articleId);
    Optional<Comment> findByIdAndArticleId(int commentId, int articleId);

    Iterable<Comment> findByAuthorId(int authorId);
    Optional<Comment> findByIdAndAuthorId(int commentId, int authorId);
}
