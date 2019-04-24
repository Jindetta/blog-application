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

    /**
     * Finds blogs posts with given author id.
     *
     * @param authorId Id of author.
     * @return All articles created by given author.
     */
    Iterable<Article> findArticlesByAuthorIdEquals(int authorId);

    /**
     * Finds all blog posts containing given parameters either in title or content of post.
     *
     * @param title String contained in title.
     * @param content String contained in content.
     * @return Blog posts found with given parameters.
     */
    Iterable<Article> findArticlesByTitleContainingIgnoreCaseOrContentContainingIgnoreCaseOrderByDateAsc(String title, String content);
}
