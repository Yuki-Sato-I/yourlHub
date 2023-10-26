package com.yourlhub.interfaces.controllers;

import com.yourlhub.domain.exceptions.GraphqlException;
import com.yourlhub.domain.models.AuthInput;
import com.yourlhub.domain.models.User;
import com.yourlhub.domain.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.ContextValue;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.Mono;

import java.util.UUID;


@Controller
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    private final UserService userService;

    private final PasswordEncoder passwordEncoder;


    @Autowired
    public UserController(final UserService userService, final PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @QueryMapping
    public Mono<User> currentUser(@ContextValue final String currentUserId) throws GraphqlException {
        return userService.findById(currentUserId);
    }

    @MutationMapping
    public Mono<String> login(@Argument AuthInput authInput) throws GraphqlException {
        return userService.findByEmailAndAuthenticatePassword(authInput);
    }

    @MutationMapping
    public Mono<String> signUp(@Argument AuthInput authInput) {
        final User user = new User(
                UUID.randomUUID().toString(),
                authInput.email(),
                "first",
                "last",
                "nick_name",
                passwordEncoder.encode(authInput.password()),
                true,
                1);

        return userService.create(user);
    }
}
