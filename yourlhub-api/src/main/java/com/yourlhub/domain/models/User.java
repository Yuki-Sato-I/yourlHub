package com.yourlhub.domain.models;

import java.sql.Timestamp;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
@AllArgsConstructor
public class User {

  private final String id;

  private final String email;

  private final String firstName;

  private String middleName;

  private final String lastName;

  private final String nickName;

  private final String passwordDigest;

  private Timestamp activatedAt;

  private final Boolean activated;

  private final Integer status;

  private Timestamp createdAt;

  private Timestamp updatedAt;
}
