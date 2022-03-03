package com.bootcamp.reto3Java.services.impl;

import com.bootcamp.reto3Java.entities.Author;
import com.bootcamp.reto3Java.repositories.AuthorRepository;
import com.bootcamp.reto3Java.services.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class AuthorServiceImpl implements AuthorService {

    @Autowired
    private AuthorRepository authorRepository;

    @Override
    public Flux<Author> findAll() {
        return authorRepository.findAll();
    }

    @Override
    public Mono<Author> save(Author request) {
        return authorRepository.save(request);
    }

    @Override
    public Mono<Void> deleteById(String id) {
        return authorRepository.deleteById(id);
    }

    @Override
    public Mono<Void> delete(Author request) {
        return authorRepository.delete(request);
    }

    @Override
    public Mono<Author> findById(String id) {
        return authorRepository.findById(id);
    }

    @Override
    public Mono<Author> updateById(String id, Author request) {
        return authorRepository.findById(id)
                .flatMap(author -> {
                    author.setEmail(request.getEmail() != null ? request.getEmail() : author.getEmail());
                    author.setBirthDate(request.getBirthDate() != null ? request.getBirthDate() : author.getBirthDate());
                    author.setPhone(request.getPhone() != null ? request.getPhone() : author.getPhone());
                    author.setName(request.getName() != null ? request.getName() : author.getName());
                    return authorRepository.save(author);
                })
                .switchIfEmpty(Mono.empty());
    }
}
