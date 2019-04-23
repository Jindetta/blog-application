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
 *
 */
@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    CommentRepository commentRepository;

    @Secured("ROLE_ADMIN")
    @PostMapping("users")
    public ResponseEntity<Void> addUser(User u, UriComponentsBuilder builder) {
        userRepository.save(u);
        UriComponents uriComponents = builder.path("users/{id}").buildAndExpand(u.getId());

        HttpHeaders header = new HttpHeaders();
        header.setLocation(uriComponents.toUri());
        return new ResponseEntity<>(header, HttpStatus.CREATED);
    }

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

    @GetMapping("users/{id:\\d+}")
    public User getUser(@PathVariable int id) {
        return userRepository.findById(id).orElseThrow(() -> new CannotFindTargetException(id, "Cannot find user with id  " +  id));
    }

    @GetMapping("users/{authorId:\\d+}/comments")
    public Iterable<Comment> getUserComments(@PathVariable int authorId) {
        return commentRepository.findByAuthorId(authorId);
    }

    @GetMapping("users")
    public Iterable<User> getUsers() {
        return userRepository.findAll();
    }
}
