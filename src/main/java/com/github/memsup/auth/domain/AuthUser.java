package com.github.memsup.auth.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class AuthUser {

    @JsonProperty("user_id")
    private int authUserId;

    @JsonProperty("username")
    private String authUsername;

    @JsonProperty("user_pwd")
    private String authPassword;

    @JsonProperty("user_email")
    private String authUserEmail;



}
