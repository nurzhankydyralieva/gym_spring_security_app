package com.epam.xstack.models.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.epam.xstack.models.enums.Permission.*;

@RequiredArgsConstructor
public enum Role {

    USER(Collections.emptySet()),
    TRAINEE(
            Set.of(
                    TRAINEE_READ,
                    TRAINEE_UPDATE,
                    TRAINEE_DELETE,
                    TRAINEE_CREATE
            )
    ),
    TRAINER(
            Set.of(
                    TRAINER_READ,
                    TRAINER_UPDATE,
                    TRAINER_DELETE,
                    TRAINER_CREATE
            )
    );

    @Getter
    private final Set<Permission> permissions;

    public List<SimpleGrantedAuthority> getAuthorities() {
        var authorities = getPermissions()
                .stream()
                .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
                .collect(Collectors.toList());
        authorities.add(new SimpleGrantedAuthority("ROLE_" + this.name()));
        return authorities;
    }
}
