package com.bootcamp.reto3Java.handlers;

import com.bootcamp.reto3Java.entities.User;
import com.bootcamp.reto3Java.services.AuthorService;
import com.bootcamp.reto3Java.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
public class UserHandler {

    @Autowired
    private UserService userService;

    @Autowired
    private AuthorService authorService;

    public Mono<ServerResponse> findAllUsers(ServerRequest request) {
        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
                .body(userService.findAll(), User.class);
    }

    public Mono<ServerResponse> saveUser(ServerRequest request) {
        return request.bodyToMono(User.class)
                // Validacion de que solo exista un registro en users con el authorId
                .flatMap(userRequest -> userService.findAll()
                        .filter(userFilter -> userFilter.getAuthorId().equals(userRequest.getAuthorId()))
                        .collectList()
                        .flatMap(users -> {
                            if (users.size() == 0) {
                                return Mono.just(userRequest);
                            } else {
                                return Mono.empty();
                            }
                        }))
                // Validacion de que el autor exista
                .flatMap(user -> authorService.findById(user.getAuthorId())
                        .flatMap(author -> ServerResponse.ok()
                                .contentType(MediaType.APPLICATION_JSON)
                                .body(userService.save(user), User.class))
                        .switchIfEmpty(ServerResponse.badRequest().build()))
                // Validacion de error en caso exista un usuario con los mismos valores
                .switchIfEmpty(ServerResponse.badRequest().build());
    }

    public Mono<ServerResponse> deleteUser(ServerRequest request) {
        String userId = request.pathVariable("id");
        return userService.findById(userId)
                .flatMap(user -> userService.delete(user).then(ServerResponse.noContent().build())
                .switchIfEmpty(ServerResponse.notFound().build()));
    }

    public Mono<ServerResponse> findUserById(ServerRequest request) {
        String userId = request.pathVariable("id");
        return userService
                .findById(userId)
                .flatMap(user -> ServerResponse.ok().body(Mono.just(user), User.class))
                .switchIfEmpty(ServerResponse.notFound().build());
    }

    public Mono<ServerResponse> updateUserById(ServerRequest request) {
        String userId = request.pathVariable("id");
        return request.bodyToMono(User.class)
                .flatMap(userRequest -> {
                    return userService.updateById(userId, userRequest)
                            .flatMap(userShow -> ServerResponse.ok().body(Mono.just(userShow), User.class))
                            .switchIfEmpty(ServerResponse.notFound().build());
                });

    }

    public Mono<ServerResponse> logIn(ServerRequest request) {
        String loginUser = request.queryParam("login").get();
        String passwordUser = request.queryParam("password").get();
        return userService.loginUser(loginUser, passwordUser)
                .flatMap(user -> ServerResponse.ok().body(Mono.just(user), User.class))
                .switchIfEmpty(ServerResponse.notFound().build());
    }

    public Mono<ServerResponse> logOff(ServerRequest request) {
        String loginUser = request.queryParam("login").get();
        String passwordUser = request.queryParam("password").get();
        return userService.logoffUser(loginUser, passwordUser)
                .flatMap(user -> ServerResponse.ok().body(Mono.just(user), User.class))
                .switchIfEmpty(ServerResponse.notFound().build());
    }
}
