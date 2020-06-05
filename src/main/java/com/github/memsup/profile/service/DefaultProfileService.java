package com.github.memsup.profile.service;

import com.github.memsup.profile.domain.Profile;
import com.github.memsup.profile.exception.ProfileNotFoundException;
import com.github.memsup.profile.repository.ProfileRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.util.function.Supplier;

@Service
@Slf4j
@RequiredArgsConstructor
public class DefaultProfileService implements ProfileService {

    private final ProfileRepository profileRepository;

    @Override
    public Supplier<Profile> getProfileWithItsUsername(String username, HttpServletResponse response) {
        return () -> {
            Profile profile = null;
            try {
                profile = profileRepository.getProfileWithItsUsername(username).orElseThrow(ProfileNotFoundException::new);
            } catch (ProfileNotFoundException e) {
                log.error(e.getMessage());
                throw new NullPointerException();
            }
            return profile;
        };
    }



}
