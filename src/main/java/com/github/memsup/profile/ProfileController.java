package com.github.memsup.profile;

import com.github.memsup.profile.domain.Profile;
import com.github.memsup.profile.exception.ProfileNotFoundException;
import com.github.memsup.profile.service.ProfileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/user")
@Slf4j
@RequiredArgsConstructor
public class ProfileController {

    private final ProfileService profileService;

    @GetMapping("/{userIdentity}")
    public ResponseEntity<CompletableFuture<Profile>> getProfile(@PathVariable String userIdentity) throws ProfileNotFoundException {
        return ResponseEntity.ok(CompletableFuture.supplyAsync(profileService.getProfileWithItsUsername(userIdentity)));
    }

}
