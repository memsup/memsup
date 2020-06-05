package com.github.memsup.profile.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Profile {

    @JsonProperty("profile_photo")
    private byte[] profilePhoto;

    @JsonProperty("profile_following")
    private short profileFollowing;

    @JsonProperty("profile_followers")
    private short profileFollowers;

    @JsonProperty("profile_wc") // wc means word counter
    private short profileWordsCounter;

    @JsonProperty("profile_score")
    private int profileScore;
}
