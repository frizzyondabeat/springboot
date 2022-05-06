package com.example.stocksv2.security.user;

import com.google.common.collect.Sets;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Set;
import java.util.stream.Collectors;

import static com.example.stocksv2.security.user.UserAuthorities.STOCK_READ;
import static com.example.stocksv2.security.user.UserAuthorities.STOCK_WRITE;

@AllArgsConstructor
@Getter
public enum UserRole {
    USER(Sets.newHashSet(STOCK_READ)),
    ADMIN(Sets.newHashSet(STOCK_READ, STOCK_WRITE));

    private final Set<UserAuthorities> permissions;

    public Set<SimpleGrantedAuthority> getGrantedAuthorities(){
        Set<SimpleGrantedAuthority> authorities = getPermissions().stream()
                .map(permissions -> new SimpleGrantedAuthority(permissions.getPermission()))
                .collect(Collectors.toSet());
        authorities.add(new SimpleGrantedAuthority("ROLE_" + this.name()));
        return authorities;
    }


}
