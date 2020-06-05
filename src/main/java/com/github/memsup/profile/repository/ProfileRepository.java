package com.github.memsup.profile.repository;

import com.github.memsup.profile.domain.Profile;

import java.util.Optional;

public interface ProfileRepository {
    Optional<Profile> getProfileWithItsUsername(String username);
}
