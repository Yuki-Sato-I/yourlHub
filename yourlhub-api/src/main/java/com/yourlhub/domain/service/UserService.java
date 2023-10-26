package com.yourlhub.domain.service;

import com.yourlhub.domain.models.AuthInput;
import com.yourlhub.domain.models.User;
import reactor.core.publisher.Mono;

public interface UserService {
    Mono<User> findById(final String id);

    Mono<String> findByEmailAndAuthenticatePassword(final AuthInput input);

    Mono<String> create(final User user);
}
