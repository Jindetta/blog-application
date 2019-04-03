package fi.tuni.backend;

import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

/**
 * Interface for user management.
 *
 * @author Joonas Lauhala {@literal <joonas.lauhala@tuni.fi>}
 *         Tuukka Juusela {@literal <tuukka.juusela@tuni.fi}
 * @version 20192802
 * @since   1.8
 */
public interface UserRepository extends CrudRepository<User, Integer> {
    Optional<User> findUserByUsername(String userName);
}
