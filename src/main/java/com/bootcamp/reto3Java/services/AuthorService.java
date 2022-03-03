package com.bootcamp.reto3Java.services;

import com.bootcamp.reto3Java.entities.Author;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface AuthorService {
    Flux<Author> findAll();
    Mono<Author> save(Author request);
    Mono<Void> deleteById(String id);
    Mono<Void> delete(Author request);
    Mono<Author> findById(String id);
    Mono<Author> updateById(String id, Author request);
}
