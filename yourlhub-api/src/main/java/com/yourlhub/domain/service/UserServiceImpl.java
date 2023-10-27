package com.yourlhub.domain.service;

import com.yourlhub.domain.exceptions.ApplicationErrors;
import com.yourlhub.domain.models.AuthInput;
import com.yourlhub.domain.models.User;
import com.yourlhub.interfaces.database.UserRepository;
import com.yourlhub.utils.JwtTokenUtil;
import java.util.Objects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class UserServiceImpl implements UserService {

  private final UserRepository userRepository;

  private final PasswordEncoder passwordEncoder;

  private final JwtTokenUtil jwtTokenUtil;

  @Autowired
  public UserServiceImpl(
      final UserRepository userRepository,
      final PasswordEncoder passwordEncoder,
      final JwtTokenUtil jwtTokenUtil) {
    this.userRepository = userRepository;
    this.passwordEncoder = passwordEncoder;
    this.jwtTokenUtil = jwtTokenUtil;
  }

  public Mono<User> findById(final String id) {
    final User user = userRepository.findById(id);
    if (Objects.isNull(user)) {
      throw ApplicationErrors.userNotFound(user.getId());
    }

    return Mono.just(user);
  }

  /**
   * emailでユーザーを検索し,パスワード認証を行う. パスワード認証に失敗した場合は例外を投げる.
   *
   * @param input
   * @return
   */
  public Mono<String> findByEmailAndAuthenticatePassword(final AuthInput input) {
    final User user = userRepository.findByEmail(input.email());

    if (Objects.isNull(user)
        || !passwordEncoder.matches(input.password(), user.getPasswordDigest())) {
      throw ApplicationErrors.userNotFound(user.getEmail());
    }

    return Mono.just(jwtTokenUtil.generateToken(user.getId()));
  }

  /**
   * ユーザーを作成し,JWTを返す.
   *
   * @param user
   * @return
   */
  public Mono<String> create(final User user) {
    userRepository.create(user);

    return Mono.just(jwtTokenUtil.generateToken(user.getId()));
  }
}
