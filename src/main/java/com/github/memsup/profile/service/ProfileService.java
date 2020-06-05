package com.github.memsup.profile.service;

import com.github.memsup.profile.domain.Profile;
import com.github.memsup.profile.exception.ProfileNotFoundException;

import java.util.function.Supplier;

public interface ProfileService {
    Supplier<Profile> getProfileWithItsUsername(String username) throws ProfileNotFoundException;
}
