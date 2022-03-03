package com.bootcamp.reto3Java.handlers;

import com.bootcamp.reto3Java.entities.Author;
import com.bootcamp.reto3Java.services.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
public class AuthorHandler {

    @Autowired
    private AuthorService authorService;

    public Mono<ServerResponse> findAll(ServerRequest request) {
        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
                .body(authorService.findAll(), Author.class);
    }

    public Mono<ServerResponse> save(ServerRequest request) {
        return request.bodyToMono(Author.class)
                .flatMap(author -> authorService.save(author))
                .flatMap(author -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(Mono.just(author), Author.class)
                .switchIfEmpty(ServerResponse.badRequest().build()));
    }

    public Mono<ServerResponse> delete(ServerRequest request) {
        String authorId = request.pathVariable("id");
        return authorService.findById(authorId)
                .flatMap(author -> authorService.delete(author).then(ServerResponse.noContent().build()))
                .switchIfEmpty(ServerResponse.notFound().build());
    }

    public Mono<ServerResponse> findById(ServerRequest request) {
        String authorId = request.pathVariable("id");
        return authorService
                .findById(authorId)
                .flatMap(author -> ServerResponse.ok().body(Mono.just(author), Author.class))
                .switchIfEmpty(ServerResponse.notFound().build());
    }

    public Mono<ServerResponse> updateById(ServerRequest request) {
        String authorId = request.pathVariable("id");
        return request.bodyToMono(Author.class)
                .flatMap(authorRequest -> {
                    return authorService.updateById(authorId, authorRequest)
                            .flatMap(authorShow -> ServerResponse.ok().body(Mono.just(authorShow), Author.class))
                            .switchIfEmpty(ServerResponse.notFound().build());
                });

    }
}
