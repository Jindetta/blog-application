package fi.tuni.backend;

import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface LikeRepository extends CrudRepository<LikeStatus, Integer> {
    Optional<LikeStatus> findByLikerIdAndCommentId(int likerId, int CommentId);
    List<LikeStatus> findLikeStatusesByCommentId(int id);
}
