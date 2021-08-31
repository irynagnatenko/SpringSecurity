package se.nackademin.backend2.user.domain;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;

public class User implements UserDetails {
    private final String username;
    private final String password;
    private final List<String> roles;

    public User(String username, String password, List<String> roles) {
        this.username = username;
        this.password = password;
        this.roles = new ArrayList<>(roles);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<GrantedAuthority> setAuths = new HashSet<>();
        for (String userRole : roles) {
            setAuths.add(new SimpleGrantedAuthority("ROLE_" + userRole));
        }

        return Collections.unmodifiableSet(setAuths);
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

}
