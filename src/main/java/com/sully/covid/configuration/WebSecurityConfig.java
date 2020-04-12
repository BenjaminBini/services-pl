package com.sully.covid.configuration;

import com.sully.covid.dal.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableWebSecurity
@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private UserService userService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // For example: Use only Http Basic and not form login.
        http.csrf().disable()
                .authorizeRequests()
                .antMatchers("/aire/**").hasAnyAuthority("ROLE_ADMIN", "ROLE_GEST")
                .antMatchers("/ct/**").hasAuthority("ROLE_ADMIN")
                .antMatchers("/relais/**").hasAuthority("ROLE_ADMIN")
                .antMatchers("/routier/**").hasAuthority("ROLE_ADMIN")
                .antMatchers("/users/**").hasAuthority("ROLE_ADMIN")
                .anyRequest()
                .authenticated()
                .and()
                .formLogin()
                .and()
                .rememberMe().key("random")
                .userDetailsService(userService);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}