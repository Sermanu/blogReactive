package com.bootcamp.reto3Java.services;

import com.bootcamp.reto3Java.entities.Blog;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface BlogService {

    Flux<Blog> findAll();
    Mono<Blog> save(Blog request);
    Mono<Void> deleteById(String id);
    Mono<Void> delete(Blog request);
    Mono<Blog> findById(String id);
    Mono<Blog> updateById(String id, Blog request);
    Mono<Blog> activateBlog(String id);

}
