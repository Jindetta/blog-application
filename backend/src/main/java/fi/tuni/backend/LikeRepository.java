package fi.tuni.backend;

import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface LikeRepository extends CrudRepository<LikeStatus, Integer> {
    Optional<LikeStatus> findByArticleIdAndAndAuthorId(int authorId, int articleId);
}
