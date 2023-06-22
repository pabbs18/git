package com.springsecurity.springsecurity.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;

import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import com.springsecurity.springsecurity.service.UserEntityUserDetailsService;


@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

        @Autowired
        private UserEntityUserDetailsService useEntityUserDetailsService;

        // authentication
        // @Bean
        // public UserDetailsService userDetailsService(PasswordEncoder encoder) {

        //         UserDetails admin = User.withUsername("Bas")
        //         .password(encoder.encode("Pwd1"))
        //         .roles("ADMIN")
        //         .build();

        //         UserDetails user = User.withUsername("John")
        //         .password(encoder.encode("Pwd2"))
        //         .roles("USER")
        //         .build();
        //         return new InMemoryUserDetailsManager(admin, user);

        //         //return new UserEntityUserDetailsService();
        // }

        @Bean
        public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
                http
                                .csrf(csrf -> csrf.disable())
                                .authorizeHttpRequests(authorize -> authorize
                                                .requestMatchers("/products/welcome", "/user/add").permitAll()
                                                .anyRequest().authenticated())
                                .formLogin(formLogin -> formLogin

                                                .permitAll())
                                .rememberMe(Customizer.withDefaults())
                                .userDetailsService(useEntityUserDetailsService)
                                ;

                return http.build();
        }

        @Bean
        public PasswordEncoder passwordEncoder() {
                return new BCryptPasswordEncoder();
        }

        

}
