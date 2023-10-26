package com.yourlhub.interceptor;

import com.yourlhub.domain.enums.ErrorTypes;
import com.yourlhub.interfaces.controllers.UserController;
import com.yourlhub.utils.JwtTokenUtil;
import graphql.GraphQLError;
import graphql.GraphqlErrorBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.server.WebGraphQlInterceptor;
import org.springframework.graphql.server.WebGraphQlRequest;
import org.springframework.graphql.server.WebGraphQlResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;


/**
 * see the https://docs.spring.io/spring-graphql/reference/transports.html#server.interception.web
 */
@Component
public class AuthInterceptor implements WebGraphQlInterceptor {
    private static final Logger logger = LoggerFactory.getLogger(AuthInterceptor.class);

    private static final List<String> nonAuthQuery = new ArrayList<>() {{
        add("login");
        add("signUp");
    }};
    private static final Integer AUTHORIZATION_HEADER_TOKEN_BEGIN_INDEX = 7;


    private final JwtTokenUtil jwtTokenUtil;

    @Autowired
    public AuthInterceptor(final JwtTokenUtil jwtTokenUtil) {
        this.jwtTokenUtil = jwtTokenUtil;
    }

    @Override
    public Mono<WebGraphQlResponse> intercept(WebGraphQlRequest request, Chain chain) {
        final String bearerToken = Objects.requireNonNullElse(request.getHeaders().getFirst(HttpHeaders.AUTHORIZATION), "XXXXXXXX");
        // 認証が必要ない場合JWT認証を行わない
        if (nonAuthQuery.contains(request.getOperationName())) {
            return chain.next(request);
        }

        final String userId = jwtTokenUtil.getUserIdFromToken(bearerToken.substring(AUTHORIZATION_HEADER_TOKEN_BEGIN_INDEX));

        // 認証が必要なメソッドなのにAuthorizationHeaderがない場合
        if (userId.isEmpty()) {
            // FIXME interceptorのみエラーの返却を独自で実装している.
            //  本当はGraphqlExceptionHandlerに実装したいが、このinterceptorで発生する例外はハンドリングしてくれない.
            //  そのためここでレスポンスのエラーを書き換えている。(認証は一番最初に通過するエラーのはずなので、置き換えで問題ない)
            return chain.next(request).map(response -> {
                final List<GraphQLError> errors = new ArrayList<>() {{
                    add(GraphqlErrorBuilder
                            .newError()
                            .errorType(ErrorTypes.UNAUTHORIZED)
                            .message("invalid authorization header")
                            .build());
                }};
                logger.error(errors.get(0).getMessage());
                return response.transform(builder -> builder.errors(errors).build());
            });
        }

        request.configureExecutionInput((executionInput, builder) ->
                builder.graphQLContext(Collections.singletonMap("currentUserId", userId)).build());
        return chain.next(request);
    }
}
