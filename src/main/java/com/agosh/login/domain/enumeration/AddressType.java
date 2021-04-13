package com.agosh.login.domain.enumeration;

/**
 * The AddressType enumeration.
 */
public enum AddressType {
    PRIMARY("primary"),
    SECONDARY("secondary"),
    DELIVERY("delivery"),
    TEMPORARY("temporary"),
    HEADOFFICE("headoffice"),
    SHOWROOM("showroom");

    private final String value;


    AddressType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
