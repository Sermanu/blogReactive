package com.bootcamp.reto3Java.services;

import com.bootcamp.reto3Java.entities.Reaction;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ReactionService {
    Flux<Reaction> findAll();
    Mono<Reaction> save(Reaction request);
    Mono<Void> deleteById(String id);
    Mono<Void> delete(Reaction request);
    Mono<Reaction> findById(String id);
    Mono<Reaction> updateById(String id, Reaction request);
}
