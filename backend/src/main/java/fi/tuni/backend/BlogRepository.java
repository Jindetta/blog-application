package fi.tuni.backend;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface BlogRepository extends CrudRepository<Article, Integer> {
    public List<Article> getArticlesByAuthorID(int authorID);
}
