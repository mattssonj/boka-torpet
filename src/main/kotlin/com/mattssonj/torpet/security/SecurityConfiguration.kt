package com.mattssonj.torpet.security

import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.web.csrf.CookieCsrfTokenRepository

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
class SecurityConfiguration : WebSecurityConfigurerAdapter() {

    override fun configure(http: HttpSecurity) {
        http
            .authorizeRequests()
            .mvcMatchers("/admin").hasAnyRole("ADMIN", "DEVELOPER")
            .anyRequest().authenticated()
            .and()
            .csrf().ignoringAntMatchers("/logout") // This is used because the react app needs to post a logout request
            .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()) // This adds the csrf to header
            .and()
            .formLogin()
            .loginPage("/login").permitAll()
            .defaultSuccessUrl("/")
            .and()
            .logout().logoutUrl("/logout").permitAll()
    }

    override fun configure(auth: AuthenticationManagerBuilder) {
        auth
            .inMemoryAuthentication()
            .passwordEncoder(BCryptPasswordEncoder())
            .withUser("user")
            .password(BCryptPasswordEncoder().encode("password"))
            .roles("USER", "DEVELOPER")
    }
}