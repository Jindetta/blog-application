package fi.tuni.backend;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true)
public class LoginConfigurer extends WebSecurityConfigurerAdapter {
    @Autowired
    private LoginEntryPoint authenticationEntryPoint;

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                .withUser("tuksu").password(passwordEncoder().encode("juksu"))
                .authorities("ROLE_ADMIN");
        auth.inMemoryAuthentication()
                .withUser("jintsu").password(passwordEncoder().encode("dintsu"))
                .authorities("ROLE_ADMIN");
        auth.inMemoryAuthentication()
                .withUser("taneli").password(passwordEncoder().encode("taikina"))
                .authorities("ROLE_USER");
        auth.inMemoryAuthentication()
                .withUser("maikki").password(passwordEncoder().encode("manaaja"))
                .authorities("ROLE_USER");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/securityNone").permitAll()
                .anyRequest().authenticated()
                .and()
                .httpBasic()
                .authenticationEntryPoint(authenticationEntryPoint);

        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.ALWAYS);

        http.addFilterAfter(new LogoutFilter("https://yle.fi", (request, response, authentication) -> {
                    System.out.println(request);
                    System.out.println(response);
                    System.out.println(authentication);
                    System.out.println("LOGGED OUT");
                }),
                BasicAuthenticationFilter.class);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
