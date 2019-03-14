package fi.tuni.backend;

import org.springframework.data.repository.CrudRepository;

public interface BlogRepository extends CrudRepository<Article, Integer> {
    Iterable<Article> findArticlesByAuthorIdEquals(int authorId);
}
