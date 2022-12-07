package com.gmail.popovychar.wuportal.domain;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.stream.Collectors;

import static java.util.Arrays.stream;

//Principal for Spring Security
public class UserPrincipal implements UserDetails {
    private WUser WUser;

    public UserPrincipal(WUser WUser) {
        this.WUser = WUser;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        //take all users auth and map and collect
        return stream(this.WUser.getAuthorities()).map(SimpleGrantedAuthority::new).collect(Collectors.toList());
    }
    //simple Authority extends grantedAuthority

    @Override
    public String getPassword() {
        return this.WUser.getPassword();
    }

    @Override
    public String getUsername() {
        return this.WUser.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return this.WUser.isNotLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return this.WUser.isActive();
    }
}
