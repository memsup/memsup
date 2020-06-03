package com.github.memsup.auth;

import com.github.enesusta.spring.security.jwt.JsonWebTokenManager;
import com.github.memsup.auth.domain.AuthUser;
import com.github.memsup.auth.repository.AuthUserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@Slf4j
@RequiredArgsConstructor
public class AuthController {

    private final JsonWebTokenManager jsonWebTokenManager;
    private final AuthUserRepository authUserRepository;
    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody final AuthUser authUser) throws UsernameNotFoundException {
        final UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(authUser.getAuthUsername(), authUser.getAuthPassword());
        authenticationManager.authenticate(authenticationToken);

        final String authToken = jsonWebTokenManager.generateToken(authUser.getAuthUsername());
        return ResponseEntity.ok(authToken);
    }

    @PostMapping("/register")
    public ResponseEntity<Boolean> register(@RequestBody final AuthUser authUser) {
        boolean result = authUserRepository.register(authUser);
        return ResponseEntity.ok(result);
    }

}
