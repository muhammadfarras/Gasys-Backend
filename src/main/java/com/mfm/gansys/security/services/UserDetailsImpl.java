package com.mfm.gansys.security.services;

import com.mfm.gansys.model.Users;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class UserDetailsImpl implements UserDetails {

    private static final long serialVersionUID = 1L;

    private Long id;

    private String email;

    private String password;

    private boolean enable;

    private Collection<? extends GrantedAuthority> authorities;



    // Buat constructor untuk membuat objek userdetailsservice implement
    public UserDetailsImpl(Long id, String email, String password, boolean enable,
                           Collection<? extends GrantedAuthority> authorities) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.enable = enable;
        this.authorities = authorities;
    }

    // mehtod to create new object, kita tidak menggunakan constructor class
    // melainkan menggunakan method ini
    public static UserDetailsImpl build(Users user){
        System.out.println("This is called when");


        List<GrantedAuthority> authorites = user.getRole().getAuthoritiesSet().stream()
                .map(role -> new SimpleGrantedAuthority(role.getName().name())
                ).collect(Collectors.toList());

        // return object UserDetailsService implement
        return new UserDetailsImpl(
                user.getId(),
                user.getEmail(),
                user.getPassword(),
                user.getIsEnable().intValue() == 1,
                authorites
        );
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
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
        return enable;
    }
}