package com.kangup.dvpis.jwt.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.kangup.dvpis.jwt.auth.JwtAuthFilter;
import com.kangup.dvpis.jwt.auth.JwtAuthenticationEntryPoint;
import com.kangup.dvpis.jwt.auth.JwtAuthenticationProvider;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private JwtAuthFilter jwtAuthFilter;

    @Autowired
    private JwtAuthenticationProvider jwtAuthenticationProvider;

    @Autowired
    private JwtAuthenticationEntryPoint jwtAuthEndPoint;

    @Override
    public void configure(AuthenticationManagerBuilder auth)  throws Exception {
        auth.authenticationProvider(jwtAuthenticationProvider);
    }
    
    @Override
    protected void configure(HttpSecurity http) throws Exception {
    	
    	//System.out.println("Configuration call!");
        http.csrf().ignoringAntMatchers("authenticateUser/**/*", "/test", "/**/*");

        http
        .csrf().disable()
        .authorizeRequests()
                .antMatchers("/authenticateUser/**/*", "/test", "/**/*")
                .permitAll()
                //.antMatchers("/**/*")
                //.hasAuthority("ROLE_USER")
                //.authenticated()
                .anyRequest().authenticated()
                .and()
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                //.addFilterBefore(AuthFilterBean(), UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling()
                .authenticationEntryPoint(jwtAuthEndPoint);
        /*.and().exceptionHandling().authenticationEntryPoint(jwtAuthEndPoint).and()
        .formLogin().disable();*/
    }
}
