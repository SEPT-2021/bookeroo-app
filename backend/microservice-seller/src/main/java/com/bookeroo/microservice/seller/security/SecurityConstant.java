package com.bookeroo.microservice.seller.security;

import java.util.concurrent.TimeUnit;

/**
 * Contains all security constants used.
 */
public class SecurityConstant {

    public static final String AUTHORIZATION_HEADER = "Authorization";
    public static final String JWT_SECRET_KEY = "WQf8Q$ueG%@^h6kQ";
    public static final String JWT_SCHEME = "Bearer ";
    public static final long JWT_EXPIRATION_TIME_MILLIS = TimeUnit.HOURS.toMillis(24);

}
