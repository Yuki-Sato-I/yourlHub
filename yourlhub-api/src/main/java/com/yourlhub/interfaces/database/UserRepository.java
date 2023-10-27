package com.yourlhub.interfaces.database;

import com.yourlhub.domain.models.User;
import com.yourlhub.interfaces.database.provider.UserSqlProvider;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.SelectProvider;

@Mapper
public interface UserRepository {

  @SelectProvider(UserSqlProvider.class)
  User findById(final String id);

  @SelectProvider(UserSqlProvider.class)
  User findByEmail(final String email);

  @Insert(
      "INSERT INTO users("
          + "id, "
          + "email, "
          + "first_name, "
          + "last_name, "
          + "nick_name, "
          + "password_digest, "
          + "activated, "
          + "status, "
          + "created_at, "
          + "updated_at"
          + ") "
          + "VALUES("
          + "CAST(#{id} AS UUID), "
          + "#{email}, "
          + "#{firstName}, "
          + "#{lastName}, "
          + "#{nickName}, "
          + "#{passwordDigest}, "
          + "#{activated}, "
          + "#{status}, "
          + "CURRENT_TIMESTAMP, "
          + "CURRENT_TIMESTAMP"
          + ")")
  Boolean create(User user);
}
