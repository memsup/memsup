package com.github.memsup.profile.service;

import com.github.memsup.profile.domain.Profile;
import com.github.memsup.profile.exception.ProfileNotFoundException;

import javax.servlet.http.HttpServletResponse;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;

public interface ProfileService {
    Supplier<Profile> getProfileWithItsUsername(String username, HttpServletResponse response) throws ProfileNotFoundException;
}
