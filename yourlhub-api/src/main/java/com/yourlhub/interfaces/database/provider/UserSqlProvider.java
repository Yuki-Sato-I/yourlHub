package com.yourlhub.interfaces.database.provider;

import com.yourlhub.domain.enums.UserStatus;
import org.apache.ibatis.builder.annotation.ProviderMethodResolver;
import org.apache.ibatis.jdbc.SQL;

public class UserSqlProvider implements ProviderMethodResolver {

  public String findById(final String id) {
    return new SQL() {
      {
        SELECT("*");
        FROM("users");
        WHERE("id = CAST(#{id} AS UUID)");
        WHERE("status = " + UserStatus.VALID.getValue());
      }
    }.toString();
  }

  public String findByEmail(final String email) {
    return new SQL() {
      {
        SELECT("*");
        FROM("users");
        WHERE("email = #{email}");
        WHERE("status = " + UserStatus.VALID.getValue());
      }
    }.toString();
  }
}
