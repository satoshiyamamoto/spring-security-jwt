package com.example.springsecurityjwt.service.security;

import com.fasterxml.jackson.annotation.JsonProperty;

public record AccessTokenResponse(
    @JsonProperty("access_token") String accessToken,
    @JsonProperty("type") String type,
    @JsonProperty("expires_in") long expiresIn,
    @JsonProperty("refresh_token") String refreshToken) {}
