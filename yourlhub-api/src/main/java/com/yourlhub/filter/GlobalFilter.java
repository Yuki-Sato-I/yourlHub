package com.yourlhub.filter;

import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;


@Component
public class GlobalFilter implements WebFilter {

    private static final Logger log = LoggerFactory.getLogger(GlobalFilter.class);

    @SneakyThrows
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        return chain.filter(exchange)
                .doOnRequest(value -> onRequest(exchange))
                .doOnSuccess(done -> onSuccess());

    }

    private void onRequest(ServerWebExchange exchange) throws IllegalArgumentException {
        log.info("onRequest");
    }

    private void onSuccess() {
        log.info("onSuccess");
    }


}
