package com.github.memsup.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.memsup.auth.domain.AuthUser;
import com.github.memsup.auth.repository.AuthUserRepository;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.exceptions.JedisException;

import java.util.Collections;

@Component
@Slf4j
@RequiredArgsConstructor
public class DefaultUserDetailsService implements UserDetailsService {

    private final AuthUserRepository authUserRepository;
    private final JedisPool jedisPool;
    private final ObjectMapper mapper;

    @SneakyThrows
    @Override
    public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {

        AuthUser authUser = null;

        try (Jedis jedis = jedisPool.getResource()) {

            final String cachedUserJsonString = jedis.hget(username, "username");

            if (cachedUserJsonString != null) {
                authUser = mapper.readValue(cachedUserJsonString, AuthUser.class);
            } else {
                authUser = authUserRepository
                        .findByUsernameOrEmail(username)
                        .orElseThrow(() -> new UsernameNotFoundException("Username not found : " + username));
                jedis.hset(username, "username", mapper.writeValueAsString(authUser));
                jedis.hset(username, "id", String.valueOf(authUser.getAuthUserId()));
            }

        } catch (JedisException e) {
            log.error(e.getMessage());
        }

        final User user = new User(authUser.getAuthUsername(), authUser.getAuthPassword(), Collections.emptyList());
        return user;

    }
}
