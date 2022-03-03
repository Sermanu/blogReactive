package com.bootcamp.reto3Java.services;


import com.bootcamp.reto3Java.entities.Comment;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface CommentService {

    Flux<Comment> findAll();
    Mono<Comment> save(Comment request);
    Mono<Void> deleteById(String id);
    Mono<Void> delete(Comment request);
    Mono<Comment> findById(String id);
    Mono<Comment> updateById(String id, Comment request);
}
