package com.kanban.configurations;

import com.kanban.services.sec.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configurers.GlobalAuthenticationConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@ComponentScan("com.kanban")
public class AuthenticationConfiguration extends GlobalAuthenticationConfigurerAdapter {

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Override
    public void init(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordencoder());
    }

    @Bean(name = "passwordEncoder")
    public PasswordEncoder passwordencoder(){
        return new BCryptPasswordEncoder();
    }
}
