package com.bookeroo.microservice.login.security;

public class SecurityConstant {

    public static final String SIGN_UP_PATHS = "/api/users/**";
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String SECRET_KEY = "JWTSecretKey";
    public static final String HEADER_STRING = "Authorization";
    public static final long EXPIRATION_TIME = 30_000;

}
