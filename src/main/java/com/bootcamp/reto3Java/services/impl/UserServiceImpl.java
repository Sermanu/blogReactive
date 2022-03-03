package com.bootcamp.reto3Java.services.impl;

import com.bootcamp.reto3Java.entities.User;
import com.bootcamp.reto3Java.repositories.UserRepository;
import com.bootcamp.reto3Java.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public Flux<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public Mono<User> save(User request) {
        return userRepository.save(request);
    }

    @Override
    public Mono<Void> deleteById(String id) {
        return userRepository.deleteById(id);
    }

    @Override
    public Mono<Void> delete(User request) {
        return userRepository.delete(request);
    }

    @Override
    public Mono<User> findById(String id) {
        return userRepository.findById(id);
    }

    @Override
    public Mono<User> updateById(String id, User request) {
        return userRepository.findById(id)
                .flatMap(user -> {
                    user.setLogin(request.getLogin() != null ? request.getLogin() : user.getLogin());
                    user.setPassword(request.getPassword() != null ? request.getPassword() : user.getPassword());
                    user.setAuthorId(request.getAuthorId() != null ? request.getAuthorId() : user.getAuthorId());
                    return userRepository.save(user);
                })
                .switchIfEmpty(Mono.empty());
    }

    public Mono<User> loginUser(String login, String password) {
        return userRepository.findAll()
                .filter(user -> user.getLogin().equals(login) && user.getPassword().equals(password))
                .collectList()
                .flatMap(users -> {
                    if (users.size() == 1) {
                        User userLogin = users.get(0);
                        userLogin.setActive(true);
                        return Mono.just(userLogin);
                    } else {
                        return Mono.empty();
                    }
                });
    }

    @Override
    public Mono<User> logoffUser(String login, String password) {
        return userRepository.findAll()
                .filter(user -> user.getLogin().equals(login) && user.getPassword().equals(password))
                .collectList()
                .flatMap(users -> {
                    if (users.size() == 1) {
                        User userLogin = users.get(0);
                        userLogin.setActive(false);
                        return Mono.just(userLogin);
                    } else {
                        return Mono.empty();
                    }
                });
    }
}
