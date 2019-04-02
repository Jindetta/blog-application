package fi.tuni.backend;

import org.springframework.security.web.authentication.logout.LogoutHandler;

public class LogoutFilter extends org.springframework.security.web.authentication.logout.LogoutFilter {
    public LogoutFilter(String logoutSuccessUrl, LogoutHandler... handlers) {
        super(logoutSuccessUrl, handlers);
    }
}
