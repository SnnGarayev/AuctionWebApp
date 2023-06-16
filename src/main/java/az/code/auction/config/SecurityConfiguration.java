package az.code.auction.config;

import az.code.auction.repo.IUserRepo;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                .cors()
                .and()
                .csrf()
                .disable()
                .authorizeHttpRequests((requests) -> requests
                        .requestMatchers("/send/**","/topic/**","/ws/**").permitAll()
                        .requestMatchers("/user/**").permitAll()
                        .requestMatchers("/api/**").permitAll()
                        .requestMatchers("/redis/**").permitAll()
                        .requestMatchers("/api/auction/**").hasRole("AUCTIONER")
                        .requestMatchers("/api/bidder/**").hasRole("BIDDER")
//                        .requestMatchers("/api/user/**").hasAnyRole("AUCTIONER","BIDDER")
                ).httpBasic();
//                http
//                .formLogin()
//                .loginPage("/login")
//                .usernameParameter("email")
//                .permitAll();

        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService(IUserRepo repo) {
        return email -> repo.findUserByEmail(email).map(user -> User.builder()
                .username(user.getEmail())
                .password(user.getPassword())
                .roles(user.getRole()).build()).orElseThrow(() -> new UsernameNotFoundException(" User Not Found"));
    }

}
