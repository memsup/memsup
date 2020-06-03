package com.github.memsup.auth.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class AuthUser {

    @JsonProperty("auth_user")
    private String authUsername;

    @JsonProperty("auth_pwd")
    private String authPassword;

    @JsonProperty("auth_user_email")
    private String authUserEmail;

}
