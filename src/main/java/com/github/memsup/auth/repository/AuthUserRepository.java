package com.github.memsup.auth.repository;

import com.github.memsup.auth.domain.AuthUser;

import java.util.Optional;

public interface AuthUserRepository {
    Optional<AuthUser> findByUsernameOrEmail(String username);
    boolean register(AuthUser authUser);
}
