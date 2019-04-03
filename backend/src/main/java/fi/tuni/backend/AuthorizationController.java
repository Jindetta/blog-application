package fi.tuni.backend;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

/**
 *
 */
@RestController
public class AuthorizationController {
    @Autowired
    private UserRepository userRepository;

    @GetMapping("permits")
    public Permits getPermissions (Authentication auth) {
        if (auth != null) {
            Optional<User> result = userRepository.findUserByUsername(auth.getName());

            return new Permits(
                result.map(user -> user.isAdmin() ? Permits.PermitTypes.ADMIN : Permits.PermitTypes.USER)
                      .orElse(Permits.PermitTypes.ANONYMOUS)
            );
        }

        return new Permits(Permits.PermitTypes.ANONYMOUS);
    }
}
