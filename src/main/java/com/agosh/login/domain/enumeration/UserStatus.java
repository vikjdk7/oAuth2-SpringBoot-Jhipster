package com.agosh.login.domain.enumeration;

/**
 * The UserStatus enumeration.
 */
public enum UserStatus {
    ACTIVE("active"),
    INACTIVE("inactive"),
    APPROVED("approved"),
    REJECTED("rejected"),
    NEW("new");

    private final String value;


    UserStatus(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
