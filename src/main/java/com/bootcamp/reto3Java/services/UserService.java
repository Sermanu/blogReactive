package com.bootcamp.reto3Java.services;

import com.bootcamp.reto3Java.entities.User;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface UserService {

    Flux<User> findAll();
    Mono<User> save(User request);
    Mono<Void> deleteById(String id);
    Mono<Void> delete(User request);
    Mono<User> findById(String id);
    Mono<User> updateById(String id, User request);
    Mono<User> loginUser(String login, String password);
    Mono<User> logoffUser(String login, String password);
}
