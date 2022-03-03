package com.bootcamp.reto3Java.services;

import com.bootcamp.reto3Java.entities.Post;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface PostService {

    Flux<Post> findAll();
    Mono<Post> save(Post request);
    Mono<Void> deleteById(String id);
    Mono<Void> delete(Post request);
    Mono<Post> findById(String id);
    Mono<Post> updateById(String id, Post request);
    Mono<Post> publicatePost(String id);
}
