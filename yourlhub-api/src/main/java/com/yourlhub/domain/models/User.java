package com.yourlhub.domain.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.sql.Timestamp;

@Getter
@RequiredArgsConstructor
@AllArgsConstructor
public class User {
    private final String id;
    private final String email;
    private final String first_name;
    private String middle_name;
    private final String last_name;
    private final String nick_name;
    private final String passwordDigest;
    private Timestamp activatedAt;
    private final Boolean activated;
    private final Integer status;
    private Timestamp createdAt;
    private Timestamp updatedAt;
}
