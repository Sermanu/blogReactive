package com.bootcamp.reto3Java.handlers;

import com.bootcamp.reto3Java.entities.Author;
import com.bootcamp.reto3Java.entities.Blog;
import com.bootcamp.reto3Java.entities.Comment;
import com.bootcamp.reto3Java.entities.Post;
import com.bootcamp.reto3Java.services.AuthorService;
import com.bootcamp.reto3Java.services.BlogService;
import com.bootcamp.reto3Java.services.CommentService;
import com.bootcamp.reto3Java.services.PostService;
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

    @Autowired
    private BlogService blogService;

    @Autowired
    private PostService postService;

    @Autowired
    private CommentService commentService;

    public Mono<ServerResponse> findAllAuthors(ServerRequest request) {
        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
                .body(authorService.findAll(), Author.class);
    }

    public Mono<ServerResponse> saveAuthor(ServerRequest request) {
        return request.bodyToMono(Author.class)
                .flatMap(author -> authorService.save(author))
                .flatMap(author -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(Mono.just(author), Author.class)
                .switchIfEmpty(ServerResponse.badRequest().build()));
    }

    public Mono<ServerResponse> deleteAuthor(ServerRequest request) {
        String authorId = request.pathVariable("id");

        return authorService.findById(authorId)
                .flatMap(author ->
                    authorService.delete(author).then(ServerResponse.noContent().build())
                )
                .switchIfEmpty(ServerResponse.notFound().build());
    }

    public Mono<ServerResponse> findAuthorById(ServerRequest request) {
        String authorId = request.pathVariable("id");
        return authorService
                .findById(authorId)
                .flatMap(author -> ServerResponse.ok().body(Mono.just(author), Author.class))
                .switchIfEmpty(ServerResponse.notFound().build());
    }

    public Mono<ServerResponse> updateAuthorById(ServerRequest request) {
        String authorId = request.pathVariable("id");
        return request.bodyToMono(Author.class)
                .flatMap(authorRequest -> {
                    return authorService.updateById(authorId, authorRequest)
                            .flatMap(authorShow -> ServerResponse.ok().body(Mono.just(authorShow), Author.class))
                            .switchIfEmpty(ServerResponse.notFound().build());
                });

    }
}
