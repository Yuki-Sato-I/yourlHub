package com.yourlhub.configs;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import org.springframework.web.server.WebSession;
import reactor.core.publisher.Mono;

@Configuration
@Component
@Slf4j
public class GlobalWebFilter implements WebFilter {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        System.out.println("filter ");
        return chain.filter(exchange)
                .doOnRequest(value -> onRequest(exchange))
                .doOnSuccess(done -> onSuccess());
    }

    private void onRequest(ServerWebExchange exchange) throws IllegalArgumentException {
        Mono<WebSession> session = exchange.getSession();
        log.info("{}", exchange.getRequest().getCookies());
        log.info("{}", exchange.getRequest().getId());
        log.info("{}", exchange.getRequest().getQueryParams());
        log.info("{}", exchange.getRequest().getRemoteAddress());
        log.info("{}", exchange.getRequest().getURI());
        log.info("{}", exchange.getRequest().getBody().map(x -> {
            System.out.println(x);
            return x;
        }));
        log.info("{}", exchange.getRequest().getPath());
        log.info("{}", exchange.getRequest().getMethod());
        log.info("{}", exchange.getRequest().getHeaders());
        log.info("{}", exchange.getRequest());
        log.info("onRequest");
    }

    private void onSuccess() {
        log.info("onSuccess");
    }

}