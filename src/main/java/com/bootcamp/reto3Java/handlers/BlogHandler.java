package com.bootcamp.reto3Java.handlers;

import com.bootcamp.reto3Java.entities.Blog;
import com.bootcamp.reto3Java.services.AuthorService;
import com.bootcamp.reto3Java.services.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Component
public class BlogHandler {

    @Autowired
    private BlogService blogService;

    @Autowired
    private AuthorService authorService;

    public Mono<ServerResponse> findAll(ServerRequest request) {
        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
                .body(blogService.findAll(), Blog.class);
    }

    public Mono<ServerResponse> save(ServerRequest request) {
        return request.bodyToMono(Blog.class)
                // Validacion de fecha del autor
                .flatMap(blog ->
                        authorService.findById(blog.getAuthorId())
                        .flatMap(author -> {
                            Date actualDate = new Date();
                            Date birthDateAuthor = author.getBirthDate();

                            LocalDate actualLocalDate = actualDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                            LocalDate birthDateAuthorLocalDate = birthDateAuthor.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

                            long diff = ChronoUnit.YEARS.between(birthDateAuthorLocalDate, actualLocalDate);

                            if (diff < 18) {
                                return ServerResponse.status(HttpStatus.PRECONDITION_REQUIRED).build();
                            } else {
                                return blogService.findAll()
                                        .filter(blog1 -> blog1.getAuthorId().equals(blog.getAuthorId()))
                                        .collectList()
                                        .flatMap(blogs -> {
                                            if (blogs.size() > 3) {
                                                return ServerResponse.status(HttpStatus.PRECONDITION_FAILED).build();
                                            } else {
                                                return ServerResponse.ok().body(blogService.save(blog), Blog.class);
                                            }
                                        });
                            }
                        })
                        .switchIfEmpty(ServerResponse.notFound().build())
                );
    }

    public Mono<ServerResponse> delete(ServerRequest request) {
        String blogId = request.pathVariable("id");
        return blogService.findById(blogId)
                .flatMap(blog -> blogService.delete(blog).then(ServerResponse.noContent().build())
                .switchIfEmpty(ServerResponse.notFound().build()));
    }

    public Mono<ServerResponse> findById(ServerRequest request) {
        String blogId = request.pathVariable("id");
        return blogService
                .findById(blogId)
                .flatMap(blog -> ServerResponse.ok().body(Mono.just(blog), Blog.class))
                .switchIfEmpty(ServerResponse.notFound().build());
    }

    public Mono<ServerResponse> updateById(ServerRequest request) {
        String blogId = request.pathVariable("id");
        return request.bodyToMono(Blog.class)
                .flatMap(blogRequest -> {
                    return blogService.updateById(blogId, blogRequest)
                            .flatMap(blogShow -> ServerResponse.ok().body(Mono.just(blogShow), Blog.class))
                            .switchIfEmpty(ServerResponse.notFound().build());
                });

    }

    public Mono<ServerResponse> activate(ServerRequest request) {
        String blogId = request.pathVariable("id");
        return blogService.activateBlog(blogId)
                            .flatMap(blogActive -> ServerResponse.ok().body(Mono.just(blogActive), Blog.class))
                            .switchIfEmpty(ServerResponse.notFound().build());
    }
}
