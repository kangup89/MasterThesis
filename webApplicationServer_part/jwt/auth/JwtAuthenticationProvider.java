package com.kangup.dvpis.jwt.auth;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import com.kangup.dvpis.api.SecondaryUser;
import com.kangup.dvpis.jwt.JwtService;
import com.kangup.dvpis.jwt.exception.JwtAuthenticationException;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;

@Component
public class JwtAuthenticationProvider implements AuthenticationProvider {

    private final JwtService jwtService;

    @SuppressWarnings("unused")
    public JwtAuthenticationProvider() {
        this(null);
    }

    @Autowired
    public JwtAuthenticationProvider(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        try {
            //Optional<SecondaryUser> possibleProfile = jwtService.verify((String) authentication.getCredentials());           
            //return new JwtAuthenticatedProfile(possibleProfile.get());
        	Jws<Claims> claims = jwtService.verify((String) authentication.getCredentials());
        	System.out.println("Provider call!");
        	System.out.println("getCredentials: " + (String) authentication.getCredentials());
        	return new JwtAuthenticatedProfile(claims);
        } catch (Exception e) {
            throw new JwtAuthenticationException("Failed to verify token", e);
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return JwtAuthToken.class.equals(authentication);
    }
}
