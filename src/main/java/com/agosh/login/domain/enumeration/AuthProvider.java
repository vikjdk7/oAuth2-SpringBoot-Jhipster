package com.agosh.login.domain.enumeration;

/**
 * The AuthProvider enumeration.
 */
public enum AuthProvider {
    local("local"),
    facebook("facebook"),
    google("google"),
    github("github");

    private final String value;


    AuthProvider(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
