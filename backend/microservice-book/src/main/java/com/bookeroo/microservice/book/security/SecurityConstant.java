package com.bookeroo.microservice.book.security;

import java.util.concurrent.TimeUnit;

public class SecurityConstant {

    public static final String SECRET_KEY = "SecretKey";
    public static final String HEADER_KEY = "Authorization";
    public static final String JWT_TOKEN_PREFIX = "Bearer ";
    public static final long JWT_EXPIRATION_TIME_MILLIS = TimeUnit.MINUTES.toMillis(30);

}
