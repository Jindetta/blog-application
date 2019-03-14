package fi.tuni.backend;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
public class UserController {

    @Autowired
    UserRepository userRepository;

    @RequestMapping(value="users", method = RequestMethod.POST)
    public ResponseEntity<Void> addUser(User u, UriComponentsBuilder builder) {
        userRepository.save(u);
        UriComponents uriComponents = builder.path("users/{id}").buildAndExpand(u.getId());

        HttpHeaders header = new HttpHeaders();
        header.setLocation(uriComponents.toUri());
        return new ResponseEntity<Void>(header, HttpStatus.CREATED);
    }

    @RequestMapping(value="users/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Void> removeUser(@PathVariable int id, UriComponentsBuilder builder) {
        try {
            userRepository.deleteById(id);
            return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
        } catch (EmptyResultDataAccessException e) {
            throw new CannotFindUserException(id,"Cannot find user with id  " +  id);
        }
    }

    @RequestMapping(value="users/{id}", method = RequestMethod.GET)
    public User getUser(@PathVariable int id) {
        return userRepository.findById(id).orElseThrow(() -> new CannotFindUserException(id, "Cannot find user with id  " +  id));
    }

    @RequestMapping(value="users", method = RequestMethod.GET)
    public Iterable<User> getUsers() {
        return userRepository.findAll();
    }
}
