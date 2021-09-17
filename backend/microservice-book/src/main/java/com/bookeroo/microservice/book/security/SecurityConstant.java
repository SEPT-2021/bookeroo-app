package com.bookeroo.microservice.book.security;

import java.util.concurrent.TimeUnit;

public class SecurityConstant {

    public static final String AUTHORIZATION_HEADER = "Authorization";
    public static final String JWT_SECRET_KEY = "SecretKey";
    public static final String JWT_SCHEME = "Bearer ";
    public static final long JWT_EXPIRATION_TIME_MILLIS = TimeUnit.MINUTES.toMillis(30);

}
