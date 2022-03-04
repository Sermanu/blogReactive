package com.bootcamp.reto3Java.services.impl;

import com.bootcamp.reto3Java.entities.Blog;
import com.bootcamp.reto3Java.repositories.BlogRepository;
import com.bootcamp.reto3Java.services.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class BlogServiceImpl implements BlogService {

    @Autowired
    private BlogRepository blogRepository;

    @Override
    public Flux<Blog> findAll() {
        return blogRepository.findAll();
    }

    @Override
    public Mono<Blog> save(Blog request) {
        request.setStatus("inactivo");
        return blogRepository.save(request);
    }

    @Override
    public Mono<Void> deleteById(String id) {
        return blogRepository.deleteById(id);
    }

    @Override
    public Mono<Void> delete(Blog request) {
        return blogRepository.delete(request);
    }

    @Override
    public Mono<Blog> findById(String id) {
        return blogRepository.findById(id);
    }

    @Override
    public Mono<Blog> updateById(String id, Blog request) {
        return blogRepository.findById(id)
                .flatMap(blog -> {
                    blog.setName(request.getName() != null ? request.getName() : blog.getName());
                    blog.setDescription(request.getDescription() != null ? request.getDescription() : blog.getDescription());
                    blog.setStatus(request.getStatus() != null ? request.getStatus() : blog.getStatus());
                    blog.setUrl(request.getUrl() != null ? request.getUrl() : blog.getUrl());
                    return blogRepository.save(blog);
                })
                .switchIfEmpty(Mono.empty());
    }

    @Override
    public Mono<Blog> activateBlog(String id) {
        return blogRepository.findById(id)
                .flatMap(blog -> {
                    blog.setStatus("activo");
                    return blogRepository.save(blog);
                })
                .switchIfEmpty(Mono.empty());
    }
}
