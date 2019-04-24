package fi.tuni.backend;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.www.BasicAuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Entry point for login.
 *
 * @author Joonas Lauhala {@literal <joonas.lauhala@tuni.fi>}
 *         Tuukka Juusela {@literal <tuukka.juusela@tuni.fi}
 * @version 20192802
 * @since   1.8
 */
@Component
public class LoginEntryPoint extends BasicAuthenticationEntryPoint {

    /**
     * Commences login.
     *
     * @param request Login request provided by Spring. Not in use.
     * @param response Response provided by Spring. Headers are added to response.
     * @param authEx AuthException provided by Spring. Not in use.
     */
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authEx) throws IOException {
        response.addHeader("WWW-Authenticate", "Basic realm=" + getRealmName());
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
    }

    /**
     * Sets name of realm to 'Kesähessut Blog application'
     *
     * @throws Exception Thrown if realm name cannot be set.
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        setRealmName("Kesahessut Blog application");
        super.afterPropertiesSet();
    }
}
