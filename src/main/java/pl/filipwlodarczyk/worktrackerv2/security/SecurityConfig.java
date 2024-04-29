package pl.filipwlodarczyk.worktrackerv2.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import pl.filipwlodarczyk.worktrackerv2.user.UserServiceImpl;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Autowired
    UserServiceImpl userService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService);
    }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(csrf -> csrf.ignoringRequestMatchers("*"))
                .authorizeHttpRequests(auth -> auth.requestMatchers("/create").permitAll())
                .authorizeHttpRequests(auth -> auth.anyRequest().authenticated())
                .headers(headers -> headers.frameOptions().sameOrigin())
                .formLogin(Customizer.withDefaults())
                .build();
    }
}
