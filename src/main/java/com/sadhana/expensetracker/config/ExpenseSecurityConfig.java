package com.sadhana.expensetracker.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class ExpenseSecurityConfig {

    @Autowired
    private UserDetailsService userDetailsService;

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder(10);
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

       return http
               .csrf(customizer -> customizer.disable())

                .authorizeHttpRequests(requests -> requests
                        .requestMatchers("/admin/**").hasRole("ADMIN")
                        .requestMatchers("/user/signup/**").hasRole("ADMIN")
                        .anyRequest().authenticated())
               .formLogin(Customizer.withDefaults())
               .httpBasic(Customizer.withDefaults())
               .build();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {

        DaoAuthenticationProvider provider =  new DaoAuthenticationProvider(userDetailsService);
        provider.setPasswordEncoder(bCryptPasswordEncoder());
        return provider;
    }

//    @Bean
//    public UserDetailsService userDetailsService() {
//
//        UserDetails santhosh = User.withDefaultPasswordEncoder()
//                                .username("santhosh")
//                                .password("Santhu@4")
//                                .roles("ADMIN")
//                                .build();
//
//        UserDetails rohith = User.withDefaultPasswordEncoder()
//                .username("rohith")
//                .password("rohi@4")
//                .roles("USER")
//                .build();
//        return new InMemoryUserDetailsManager(santhosh, rohith);
//    }

}
