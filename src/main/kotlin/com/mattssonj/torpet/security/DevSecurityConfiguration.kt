package com.mattssonj.torpet.security

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Profile
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.provisioning.JdbcUserDetailsManager
import org.springframework.security.provisioning.UserDetailsManager
import org.springframework.security.web.csrf.CookieCsrfTokenRepository
import javax.sql.DataSource

@Profile("dev")
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
class DevSecurityConfiguration(private val datasource: DataSource) : WebSecurityConfigurerAdapter() {

    override fun configure(http: HttpSecurity) {
        http
            .authorizeRequests()
            .mvcMatchers("/admin").hasAnyRole(Roles.ADMIN, Roles.DEV)
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
            .jdbcAuthentication()
            .dataSource(datasource)
            .passwordEncoder(passwordEncoder)
    }

    @Bean fun userDetailsManager(): UserDetailsManager = JdbcUserDetailsManager(datasource)

}