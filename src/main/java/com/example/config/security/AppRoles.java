package com.example.config.security;

import java.util.List;

/**
 * Constants for Spring Security authorities.
 */
public final class AppRoles {

    public static final String ADMIN = "admin-panel-admin";
    public static final String USER = "admin-panel-user";
    public static final String REPORTER = "admin-panel-reporter";
    public static final String ANONYMOUS = "ANONYMOUS";
    public static final String[] ALL_ROLES_ARRAY = { ADMIN, USER, REPORTER };
    public static final List<String> ALL_ROLES = List.of(ADMIN, USER, REPORTER);

    private AppRoles() {}
}
