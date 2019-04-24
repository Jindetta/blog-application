package fi.tuni.backend;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.security.core.Authentication;
import org.springframework.web.servlet.ModelAndView;

import java.util.Optional;

/**
 *
 */
@RestController
public class AuthorizationController {

    /**
     *
     */
    @Autowired
    private UserRepository userRepository;

    /**
     *
     * @param auth
     * @return
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
     *
     * @return
     */
    @GetMapping("/authenticate")
    public ModelAndView promptAuthentication() {
        return new ModelAndView("redirect:/");
    }
}
