package com.github.memsup.configuration;

import com.github.memsup.auth.DefaultUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.userdetails.UserDetailsService;

@Configuration
@RequiredArgsConstructor
public class UserDetailsServiceConfiguration {

    private final DefaultUserDetailsService userDetailsService;

    @Primary
    @Bean
    public UserDetailsService userDetailsService() {
        return userDetailsService;
    }

}
