package fi.tuni.backend;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.security.core.Authentication;
import org.springframework.web.servlet.ModelAndView;

import java.util.Optional;

/**
 * Controller for authorization
 *
 * @author Joonas Lauhala {@literal <joonas.lauhala@tuni.fi>}
 *         Tuukka Juusela {@literal <tuukka.juusela@tuni.fi}
 * @version 20192802
 * @since   1.8
 */
@RestController
public class AuthorizationController {

    /**
     * Repository for getting blog applications users.
     */
    @Autowired
    private UserRepository userRepository;

    /**
     * Returns role of the user. Returns ANONYMOUS if user cannot be found from user database.
     *
     * @param auth Used to get users username and return correct level of permission. Provided by Spring.
     * @return Role of the user. Either ADMIN, USER or ANONYMOUS
     */
    @GetMapping("/role")
    public Role getRole(Authentication auth) {
        if (auth != null) {
            Optional<User> result = userRepository.findUserByUsername(auth.getName());

            return new Role(
                result.map(user -> user.isAdmin() ? Role.RoleTypes.ADMIN : Role.RoleTypes.USER)
                      .orElse(Role.RoleTypes.ANONYMOUS),
                result.map(User::getId).orElse(-1)
            );
        }

        return new Role(Role.RoleTypes.ANONYMOUS, -1);
    }

    /**
     * Prompts authentication.
     *
     * @return Model for next view.
     */
    @GetMapping("/authenticate")
    public ModelAndView promptAuthentication() {
        return new ModelAndView("redirect:/");
    }
}
