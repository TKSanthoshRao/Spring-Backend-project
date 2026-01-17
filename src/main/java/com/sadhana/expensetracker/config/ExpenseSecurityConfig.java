package com.sadhana.expensetracker.config;

import com.sadhana.expensetracker.filters.JwtFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.switchuser.SwitchUserFilter;

@Configuration
@EnableWebSecurity
public class ExpenseSecurityConfig {

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private JwtFilter jwtFilter;

    @Autowired
    private AuthenticationConfiguration  authenticationConfiguration;

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder(10);
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

       return http
               .csrf(customizer -> customizer.disable())
               .cors(Customizer.withDefaults())
                .authorizeHttpRequests(requests -> requests
                        .requestMatchers("/admin/**").hasRole("ADMIN")
                        .requestMatchers("/user/signup/**").permitAll()
                        .requestMatchers("/api/auth/me").permitAll()
                        .anyRequest().authenticated())
               .httpBasic(Customizer.withDefaults())
               .addFilterBefore(jwtFilter,UsernamePasswordAuthenticationFilter.class)
               .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
               .build();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {

        DaoAuthenticationProvider provider =  new DaoAuthenticationProvider(userDetailsService);
        provider.setPasswordEncoder(bCryptPasswordEncoder());
        return provider;
    }

    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration config
    ) throws Exception {
        return config.getAuthenticationManager();
    }


//    @Bean
//    public SwitchUserFilter switchUserFilter() {
//            SwitchUserFilter filter = new SwitchUserFilter();
//            filter.setUserDetailsService(userDetailsService);
//            filter.setSwitchUserUrl("/login/impersonate"); // URL to initiate impersonation
//            filter.setExitUserUrl("/logout/impersonate"); // URL to switch back
//            filter.setTargetUrl("/"); // Default success URL
//            return filter;
//        }
//    }

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
