package com.github.memsup.configuration;

import com.github.enesusta.spring.security.jwt.JsonWebTokenManager;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JsonWebTokenConfiguration {

    @Value("jwt.secret.key")
    private String secretKey;

    @Value("jwt.secret.validity")
    private String validity;

    @Bean
    public JsonWebTokenManager jsonWebTokenManager() {
        return new JsonWebTokenManager(secretKey, Integer.parseInt(validity));
    }

}
