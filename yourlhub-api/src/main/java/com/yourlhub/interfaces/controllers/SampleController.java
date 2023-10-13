package com.yourlhub.interfaces.controllers;

import com.yourlhub.domain.models.SampleAuthor;
import com.yourlhub.domain.models.SampleBook;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.BatchMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Controller
public class SampleController {
    @QueryMapping
    public Mono<SampleBook> bookById(@Argument String id) {
        return Mono.just(SampleBook.getById(id));
    }

    @QueryMapping
    public Flux<SampleBook> books() {
        return Flux.fromStream(SampleBook.get().stream());
    }

    @BatchMapping
    public Flux<SampleAuthor> sampleAuthor(List<SampleBook> sampleBooks) {
        final List<String> ids = sampleBooks.stream().map(SampleBook::authorId).toList();
        final Map<String, SampleAuthor> authors = SampleAuthor
                .getByIds(ids)
                .stream()
                .collect(Collectors.toMap(SampleAuthor::id, Function.identity()));
        System.out.println(authors);
        return Flux.fromStream(ids.stream().map(authors::get));
    }
}
