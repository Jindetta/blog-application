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
    @GetMapping("/permits")
    public Permits getPermissions (Authentication auth) {
        if (auth != null) {
            Optional<User> result = userRepository.findUserByUsername(auth.getName());

            return new Permits(
                result.map(user -> user.isAdmin() ? Permits.PermitTypes.ADMIN : Permits.PermitTypes.USER)
                      .orElse(Permits.PermitTypes.ANONYMOUS),
                result.map(User::getId).orElse(-1)
            );
        }

        return new Permits(Permits.PermitTypes.ANONYMOUS, -1);
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
