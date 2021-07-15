package com.example.blogplatform.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Set;
import java.util.stream.Collectors;

import static com.example.blogplatform.security.Permission.*;

public enum Role {
    ADMIN(Set.of(USER_READ, USER_UPDATE, USER_DELETE, USER_MANAGEMENT, POST_READ, POST_CREATE, POST_UPDATE, POST_DELETE, POST_MANAGEMENT, TAG_CREATE, TAG_UPDATE, TAG_DELETE, TAG_MANAGEMENT, COMMENT_READ, COMMENT_CREATE, COMMENT_UPDATE, COMMENT_DELETE, COMMENT_MANAGEMENT)),
    USER(Set.of(USER_READ, USER_UPDATE, USER_DELETE, POST_READ, POST_CREATE, POST_UPDATE, POST_DELETE, TAG_CREATE, TAG_UPDATE, COMMENT_READ, COMMENT_CREATE, COMMENT_UPDATE, COMMENT_DELETE));

    private final Set<Permission> permissions;

    Role(Set<Permission> permissions) {
        this.permissions = permissions;
    }

    public Set<Permission> getPermissions() {
        return permissions;
    }

    public Set<? extends GrantedAuthority> getGrantedAuthority(){
        Set<SimpleGrantedAuthority> authorities = permissions.stream()
                .map(authority -> new SimpleGrantedAuthority(authority.getPermission()))
                .collect(Collectors.toSet());
        authorities.add(new SimpleGrantedAuthority("ROLE_" + this.name()));
        return authorities;
    }
}
