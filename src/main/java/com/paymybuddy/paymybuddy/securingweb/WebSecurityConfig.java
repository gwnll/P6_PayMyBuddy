package com.paymybuddy.paymybuddy.securingweb;

import com.paymybuddy.paymybuddy.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private AuthenticationService authenticationService;

    @Override
    protected UserDetailsService userDetailsService() {
        return this.authenticationService;
    }

    //    @Override
//    protected void configure(final AuthenticationManagerBuilder auth) throws Exception {
//        // authentication manager (see below)
//        auth.authenticationProvider(new )
//    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // http builder configurations for authorize requests and form login (see below)
        http    .csrf().disable()
                .authorizeRequests()
                .antMatchers("/admin/**").hasRole("ADMIN")
//                .antMatchers("/anonymous*").anonymous()
                .antMatchers("/", "/home", "/index", "/css/**", "/login*").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin()
//                .loginPage("/login")
                .defaultSuccessUrl("/", true)
                .and()
                .httpBasic()
                .and()
                .rememberMe();

    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}