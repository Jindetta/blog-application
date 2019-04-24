package fi.tuni.backend;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Configuration class for login events.
 *
 * @author Joonas Lauhala {@literal <joonas.lauhala@tuni.fi>}
 *         Tuukka Juusela {@literal <tuukka.juusela@tuni.fi}
 * @version 20192802
 * @since   1.8
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true)
public class LoginConfigurer extends WebSecurityConfigurerAdapter {

    /**
     * Stores login entry point.
     */
    @Autowired
    private LoginEntryPoint authenticationEntryPoint;

    /**
     * Store user repository.
     */
    @Autowired
    private UserRepository repository;

    /**
     * Configures global information.
     *
     * @param auth  AuthenticationManagerBuilder instance.
     */
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) {
        PasswordEncoder encoder = passwordEncoder();

        List<User> users = Stream.of(
                new User("tuksu",  encoder.encode("juksu"), true),
                new User("taneli",  encoder.encode("taukki")),
                new User("maikki", encoder.encode("manaaja")),
                new User("jindetta",  encoder.encode("123"), true)
        ).collect(Collectors.toList());
        repository.saveAll(users);

        users.forEach(user -> {
            try {
                auth.inMemoryAuthentication()
                        .withUser(user.getUsername()).password(user.getPassword())
                        .authorities(user.isAdmin() ? "ROLE_ADMIN" : "ROLE_USER");
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    /**
     * Configures basic authentication and session requests.
     *
     * @param http          HttpSecurity instance.
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http    .sessionManagement()
                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                    .and()
                .httpBasic()
                    .authenticationEntryPoint(authenticationEntryPoint)
                    .and()
                .authorizeRequests()
                    .antMatchers("/", "/api/**")
                    .permitAll()
                    .antMatchers("/authenticate")
                    .authenticated()
                    .and()
                .csrf()
                    .disable();
    }

    /**
     * Configures resources.
     *
     * @param web   WebSecurity instance.
     */
    @Override
    public void configure(WebSecurity web) {
        web.ignoring().antMatchers("/*.*", "/static/**");
    }

    /**
     * Returns PasswordEncoder.
     *
     * @return PasswordEncoder object.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
