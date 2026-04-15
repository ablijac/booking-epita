package dev._xdbe.booking.creelhouse.infrastructure.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.session.web.http.CookieSerializer;
import org.springframework.session.web.http.DefaultCookieSerializer;
import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
            .authorizeHttpRequests(auth -> auth
                // Step 4a: add access control
                .requestMatchers("/dashboard").hasRole("ADMIN")
                .anyRequest().permitAll()
            )
		    .formLogin(withDefaults())
            // Step 4b: Add login form
            // ...
            // Step 4b: End of login form configuration
            
            .csrf((csrf) -> csrf
                .ignoringRequestMatchers("/h2-console/**")
            )
            .headers(headers ->
                headers.frameOptions(frameOptions ->
                    frameOptions.disable()
                )
            )
            .build();
    }

    UserDetails administrator = User.builder()
            .username("admin")
            .password("{bcrypt}$2a$12$.K/z91XVdt8ckF.sDmHGDezwEZ5ErN.V.Shuzcm1RUqTBkBqycD5i")
            .roles("ADMIN")
            .build();
    UserDetails guest = User.builder()
            .username("guest")
            .password("{bcrypt}$2a$12$.K/z91XVdt8ckF.sDmHGDezwEZ5ErN.V.Shuzcm1RUqTBkBqycD5i")
            .roles("GUEST")
            .build();
    @Bean
    public UserDetailsService userDetailsService() {
        return new InMemoryUserDetailsManager(administrator, guest);
    }
    // Step 3: add InMemoryUserDetailsManager
    // ...
    // Step 3: end

}