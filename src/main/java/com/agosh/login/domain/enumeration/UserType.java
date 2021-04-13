package com.agosh.login.domain.enumeration;

/**
 * The UserType enumeration.
 */
public enum UserType {
    ADMIN("admin"),
    PARTNER("partner"),
    SELLER("seller"),
    DIRECTSELLER("directseller"),
    BUYER("buyer");

    private final String value;


    UserType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
