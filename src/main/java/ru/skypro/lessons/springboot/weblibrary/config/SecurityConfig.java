package ru.skypro.lessons.springboot.weblibrary.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AuthorizeHttpRequestsConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import ru.skypro.lessons.springboot.weblibrary.securriti.Role;

@EnableWebSecurity
@Configuration
public class SecurityConfig {

    private final UserDetailsService userDetailsService;

    @Autowired
    public SecurityConfig(@Qualifier("securityUserDetailsService") UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService);
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.csrf().disable()
                .authenticationProvider(authenticationProvider())
                .authorizeHttpRequests(this::customizeRequest);
        return httpSecurity.build();
    }

    private void customizeRequest(AuthorizeHttpRequestsConfigurer<HttpSecurity>.AuthorizationManagerRequestMatcherRegistry registry) {
        try {
            registry.requestMatchers(HttpMethod.PUT)
                    .hasAuthority(Role.ADMIN.getRole())
                    .requestMatchers(HttpMethod.POST)
                    .hasAuthority(Role.ADMIN.getRole())
                    .requestMatchers(HttpMethod.DELETE)
                    .hasAuthority(Role.ADMIN.getRole())
                    .requestMatchers(HttpMethod.GET)
                    .hasAnyAuthority(Role.ADMIN.getRole(), Role.USER.getRole())
                    .and()
                    .formLogin().permitAll()
                    .defaultSuccessUrl("/emploees")
                    .and()
                    .logout().logoutUrl("/logout");

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
}
