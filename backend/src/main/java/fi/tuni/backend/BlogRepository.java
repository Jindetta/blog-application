package fi.tuni.backend;

import org.springframework.data.repository.CrudRepository;

/**
 * Interface for blog management.
 *
 * @author Joonas Lauhala {@literal <joonas.lauhala@tuni.fi>}
 *         Tuukka Juusela {@literal <tuukka.juusela@tuni.fi}
 * @version 20192802
 * @since   1.8
 */
public interface BlogRepository extends CrudRepository<Article, Integer> {
    Iterable<Article> findArticlesByAuthorIdEquals(int authorId);
    Iterable<Article> findArticlesByTitleContainingIgnoreCaseOrContentContainingIgnoreCaseOrderByDateAsc(String title, String content);
}
