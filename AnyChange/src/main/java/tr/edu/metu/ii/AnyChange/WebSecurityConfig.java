package tr.edu.metu.ii.AnyChange;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import tr.edu.metu.ii.AnyChange.user.models.User;
import tr.edu.metu.ii.AnyChange.user.models.UserRole;
import tr.edu.metu.ii.AnyChange.user.services.UserService;

@Configuration
@EnableWebSecurity
@AllArgsConstructor
public class WebSecurityConfig {
    final private UserService userService;
    final private BCryptPasswordEncoder passwordEncoder;
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests((requests) -> requests
                    .requestMatchers("/", "/home", "/signup", "/confirm*", "/resendConfirmationToken*", "/forgotPassword", "/resetPassword*").permitAll()
                    .anyRequest().authenticated()
            )
            .formLogin((form) -> form
                    .loginPage("/login")
                    .permitAll()
            )
            .logout(LogoutConfigurer::permitAll);
        return http.build();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        User user = new User();
        user.setEmail("taylan.isikoglu@gmail.com");
        user.setFirstName("admin");
        user.setLastName("admin");
        user.setPassword(passwordEncoder.encode("123123123."));
        user.setRole(UserRole.SELLER);
        user.setEnabled(true);
        userService.createUser(user);
        auth.userDetailsService(userService).passwordEncoder(passwordEncoder);
    }
}