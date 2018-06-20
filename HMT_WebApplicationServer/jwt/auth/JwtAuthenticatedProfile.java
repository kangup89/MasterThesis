package com.kangup.dvpis.jwt.auth;

import java.util.Collection;
import java.util.Collections;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import com.kangup.dvpis.api.SecondaryUser;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;

public class JwtAuthenticatedProfile implements Authentication {

    //private final SecondaryUser secondaryUser;
    private final Jws<Claims> claims;
    

    /*public JwtAuthenticatedProfile(SecondaryUser secondaryUser) {
        this.secondaryUser = secondaryUser;
    }*/
    
    public JwtAuthenticatedProfile(Jws<Claims> claims) {
        this.claims = claims;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"));
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getDetails() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return null;
    }

    @Override
    public boolean isAuthenticated() {
        return true;
    }

    @Override
    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {

    }


    @Override
    public String getName() {
        return claims.getBody().getSubject();
    }
}
