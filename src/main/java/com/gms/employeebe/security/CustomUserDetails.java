package com.gms.employeebe.security;

import com.gms.employeebe.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

/**
 * Ánh xạ entity User sang UserDetails cho Spring Security.
 */
@RequiredArgsConstructor
public class CustomUserDetails implements UserDetails {

    private final User user;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // Dùng enum: không dùng isBlank()/toUpperCase() trên enum
        // Cách 1 (nếu bạn thêm asAuthority() như mục 2):
        // String authority = user.getRole() != null ? user.getRole().asAuthority() : "ROLE_USER";

        // Cách 2 (giữ nguyên enum như hiện tại):
        String roleName = user.getRole() != null ? user.getRole().name() : "USER";
        String authority = "ROLE_" + roleName;

        return List.of(new SimpleGrantedAuthority(authority));
    }

    @Override public String getPassword() { return user.getPassword(); }
    @Override public String getUsername() { return user.getUsername(); }

    @Override public boolean isAccountNonExpired()     { return true; }
    @Override public boolean isAccountNonLocked()      { return true; }
    @Override public boolean isCredentialsNonExpired() { return true; }
    @Override public boolean isEnabled()               { return true; }

    // Tuỳ chọn: nếu cần dùng tới entity gốc ở nơi khác
    public User getUser() { return user; }
}