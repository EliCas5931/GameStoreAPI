package com.company.adminapiservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import javax.sql.DataSource;
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    DataSource dataSource;
    @Autowired
    public void configAuthentication(AuthenticationManagerBuilder authBuilder) throws Exception {
        PasswordEncoder encoder = new BCryptPasswordEncoder();
        authBuilder.jdbcAuthentication()
                .dataSource(dataSource)
                .usersByUsernameQuery(
                        "select username, password, enabled from users where username = ?")
                .authoritiesByUsernameQuery(
                        "select username, authority from authorities where username = ?")
                .passwordEncoder(encoder);
    }
    public void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.httpBasic();
        httpSecurity.authorizeRequests()
                //employee access
                .mvcMatchers(HttpMethod.GET, "/customers").hasAuthority("ROLE_EMPLOYEE")
                .mvcMatchers(HttpMethod.GET, "/customers/{id}").hasAuthority("ROLE_EMPLOYEE")
                .mvcMatchers(HttpMethod.GET, "/inventory").hasAuthority("ROLE_EMPLOYEE")
                .mvcMatchers(HttpMethod.GET, "/inventory/{id}").hasAuthority("ROLE_EMPLOYEE")
                .mvcMatchers(HttpMethod.GET, "/product").hasAuthority("ROLE_EMPLOYEE")
                .mvcMatchers(HttpMethod.GET, "/product/{id}").hasAuthority("ROLE_EMPLOYEE")
                .mvcMatchers(HttpMethod.GET, "/invoice").hasAuthority("ROLE_EMPLOYEE")
                .mvcMatchers(HttpMethod.GET, "/invoice/{id}").hasAuthority("ROLE_EMPLOYEE")
                .mvcMatchers(HttpMethod.GET, "/levelUp").hasAuthority("ROLE_EMPLOYEE")
                .mvcMatchers(HttpMethod.GET, "/levelUp/{id}").hasAuthority("ROLE_EMPLOYEE")
                .mvcMatchers(HttpMethod.PUT, "/inventory/{id}").hasAuthority("ROLE_EMPLOYEE")
                //team access
                .mvcMatchers(HttpMethod.PUT, "/customers/{id}").hasAuthority("ROLE_TEAM")
                .mvcMatchers(HttpMethod.PUT, "/invoice/{id}").hasAuthority("ROLE_TEAM")
                .mvcMatchers(HttpMethod.PUT, "/product/{id}").hasAuthority("ROLE_TEAM")
                .mvcMatchers(HttpMethod.PUT, "/levelUp/{id}").hasAuthority("ROLE_TEAM")
                .mvcMatchers(HttpMethod.POST, "/customers").hasAuthority("ROLE_TEAM")
                //manager access
                .mvcMatchers(HttpMethod.POST, "/product").hasAuthority("ROLE_MANAGER")
                .mvcMatchers(HttpMethod.POST, "/invoice").hasAuthority("ROLE_MANAGER")
                .mvcMatchers(HttpMethod.POST, "/inventory").hasAuthority("ROLE_MANAGER")
                .mvcMatchers(HttpMethod.POST, "/levelUp").hasAuthority("ROLE_MANAGER")
                //admin access
                .mvcMatchers(HttpMethod.DELETE, "/product/{id}").hasAuthority("ROLE_ADMIN")
                .mvcMatchers(HttpMethod.DELETE, "/customer/{id}").hasAuthority("ROLE_ADMIN")
                .mvcMatchers(HttpMethod.DELETE, "/invoice/{id}").hasAuthority("ROLE_ADMIN")
                .mvcMatchers(HttpMethod.DELETE, "/inventory/{id}").hasAuthority("ROLE_ADMIN")
                .mvcMatchers(HttpMethod.DELETE, "/levelUp/{id}").hasAuthority("ROLE_ADMIN")
                //        Any user (both authenticated and unauthenticated) can search for products.
                .anyRequest().permitAll();
        httpSecurity
                .logout()
                .clearAuthentication(true)
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutSuccessUrl("/allDone")
                .deleteCookies("JSESSIONID")
                .deleteCookies("XSRF-TOKEN")
                .invalidateHttpSession(true);
        httpSecurity
                .csrf()
                .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse());
    }
}