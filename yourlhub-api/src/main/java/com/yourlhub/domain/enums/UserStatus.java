package com.yourlhub.domain.enums;

public enum UserStatus {
    INVALID(0),
    VALID(1);

    private final Integer value;

    UserStatus(Integer value) {
        this.value = value;
    }

    public Integer getValue() {
        return value;
    }
}
