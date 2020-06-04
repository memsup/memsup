package com.github.memsup.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.memsup.auth.domain.AuthUser;
import com.github.memsup.auth.repository.DefaultAuthRepository;
import com.github.memsup.configuration.JsonWebTokenConfiguration;
import com.github.memsup.configuration.PasswordConfiguration;
import com.github.memsup.configuration.PostgreDataSourceConfiguration;
import com.github.memsup.configuration.RedisDataSourceConfiguration;
import com.github.memsup.configuration.UserDetailsServiceConfiguration;
import com.github.memsup.configuration.WebSecurityConfiguration;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.jupiter.api.Assertions.*;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = AuthController.class)

@ContextConfiguration(classes = {
        AuthFilter.class,
        DefaultUserDetailsService.class,
        DefaultAuthRepository.class,
        JsonWebTokenConfiguration.class,
        PasswordConfiguration.class,
        WebSecurityConfiguration.class,
        UserDetailsServiceConfiguration.class,
        PostgreDataSourceConfiguration.class,
        RedisDataSourceConfiguration.class
})
class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void should_returnToken_when_postRequestArrive() throws Exception {

        AuthUser authUser = new AuthUser();
        authUser.setAuthUserId(1);
        authUser.setAuthUsername("enesusta");
        authUser.setAuthPassword(passwordEncoder.encode("testpwd"));
        authUser.setAuthUserEmail("enesusta@email.com");

        final String requestJSON = objectMapper.writeValueAsString(authUser);

        final MockHttpServletRequestBuilder httpServletRequestBuilder = MockMvcRequestBuilders
                .post("/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJSON);

        Matcher<String> matcher = new Matcher<String>() {
            @Override
            public boolean matches(Object o) {
                System.out.println("token is= " + o);
                return false;
            }

            @Override
            public void describeMismatch(Object o, Description description) {

            }

            @Override
            public void _dont_implement_Matcher___instead_extend_BaseMatcher_() {

            }

            @Override
            public void describeTo(Description description) {

            }
        };


        mockMvc.perform(httpServletRequestBuilder)
                //.andExpect(status().isOk())
                .andExpect(content().string(matcher));

    }

}