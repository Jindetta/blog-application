package fi.tuni.backend;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@Scope("session")
public class AuthorizationController {
    @Autowired
    private UserRepository userRepository;

    @GetMapping("permits")
    public Permits getPermissions (Authentication auth) {
        Optional<User> optional = userRepository.findUserByUsername(auth.getName());

        String permission = optional.isPresent()? optional.get().isAdmin()?Permits.ADMIN:Permits.USER :Permits.ANONYMOUS;

        return new Permits(permission);
    }
}
