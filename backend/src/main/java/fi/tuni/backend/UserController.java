package fi.tuni.backend;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

/**
 * Controller for getting blog data.
 *
 * @author Joonas Lauhala {@literal <joonas.lauhala@tuni.fi>}
 *         Tuukka Juusela {@literal <tuukka.juusela@tuni.fi}
 * @version 20192802
 * @since   1.8
 */
@RestController
@RequestMapping("/api")
public class UserController {

    /**
     * Repository of users.
     */
    @Autowired
    UserRepository userRepository;

    /**
     * Repository of comments.
     */
    @Autowired
    CommentRepository commentRepository;

    /**
     * Adds user to a database.
     * @param u User to add.
     * @param builder Used to build header of response.
     * @return Returns location of created user.
     */
    @Secured("ROLE_ADMIN")
    @PostMapping("users")
    public ResponseEntity<Void> addUser(User u, UriComponentsBuilder builder) {
        userRepository.save(u);
        UriComponents uriComponents = builder.path("users/{id}").buildAndExpand(u.getId());

        HttpHeaders header = new HttpHeaders();
        header.setLocation(uriComponents.toUri());
        return new ResponseEntity<>(header, HttpStatus.CREATED);
    }

    /**
     * Removes user from user database.
     *
     * @param id Id of user to remove.
     * @return Status NO_CONTENT if successful. Throws CannotFindTargetException if user is not found.
     */
    @Secured("ROLE_ADMIN")
    @DeleteMapping("users/{id:\\d+}")
    public ResponseEntity<Void> removeUser(@PathVariable int id, UriComponentsBuilder builder) {
        try {
            userRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (EmptyResultDataAccessException e) {
            throw new CannotFindTargetException(id,"Cannot find user with id  " +  id);
        }
    }

    /**
     * Returns user with given id.
     *
     * @param id Id of user.
     * @return User with given id.
     */
    @GetMapping("users/{id:\\d+}")
    public User getUser(@PathVariable int id) {
        return userRepository.findById(id).orElseThrow(() -> new CannotFindTargetException(id, "Cannot find user with id  " +  id));
    }

    /**
     * Returns all of user's comments.
     *
     * @param authorId Authors Id to get comment from.
     * @return Comment made by given user.
     */
    @GetMapping("users/{authorId:\\d+}/comments")
    public Iterable<Comment> getUserComments(@PathVariable int authorId) {
        return commentRepository.findByAuthorId(authorId);
    }

    /**
     * Return all of users in user repository.
     *
     * @return All users in user repository.
     */
    @GetMapping("users")
    public Iterable<User> getUsers() {
        return userRepository.findAll();
    }
}
