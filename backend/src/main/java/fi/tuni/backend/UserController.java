package fi.tuni.backend;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {

    @Autowired
    UserRepository userRepository;

    @RequestMapping(value="users", method = RequestMethod.POST)
    public void addUser(User u) {
        userRepository.save(u);
    }

    @RequestMapping(value="users/{id}", method = RequestMethod.DELETE)
    public void removeUser(@PathVariable int id) {
        userRepository.deleteById(id);
    }

    @RequestMapping(value="users/{id}", method = RequestMethod.GET)
    public User getUser(@PathVariable int id) {
        return userRepository.findById(id).orElse(null);
    }

    @RequestMapping(value="users", method = RequestMethod.GET)
    public Iterable<User> getUsers() {
        return userRepository.findAll();
    }
}
