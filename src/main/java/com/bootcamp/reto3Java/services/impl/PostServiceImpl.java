package com.bootcamp.reto3Java.services.impl;

import com.bootcamp.reto3Java.entities.Post;
import com.bootcamp.reto3Java.repositories.PostRepository;
import com.bootcamp.reto3Java.services.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class PostServiceImpl implements PostService {

    @Autowired
    private PostRepository postRepository;

    @Override
    public Flux<Post> findAll() {
        return postRepository.findAll();
    }

    @Override
    public Mono<Post> save(Post request) {
        request.setStatus("borrador");
        return postRepository.save(request);
    }

    @Override
    public Mono<Void> deleteById(String id) {
        return postRepository.deleteById(id);
    }

    @Override
    public Mono<Void> delete(Post request) {
        return postRepository.delete(request);
    }

    @Override
    public Mono<Post> findById(String id) {
        return postRepository.findById(id);
    }

    @Override
    public Mono<Post> updateById(String id, Post request) {
        return postRepository.findById(id)
                .flatMap(post -> {
                    post.setTitle(request.getTitle() != null ? request.getTitle() : post.getTitle());
                    post.setDate(request.getDate() != null ? request.getDate() : post.getDate());
                    post.setStatus(request.getStatus() != null ? request.getStatus() : post.getStatus());
                    post.setContent(request.getContent() != null ? request.getContent() : post.getContent());
                    post.setBlogId(request.getBlogId() != null ? request.getBlogId() : post.getBlogId());
                    return postRepository.save(post);
                })
                .switchIfEmpty(Mono.empty());
    }

    @Override
    public Mono<Post> publicatePost(String id) {
        return postRepository.findById(id)
                .flatMap(post -> {
                    post.setStatus("publicado");
                    return postRepository.save(post);
                })
                .switchIfEmpty(Mono.empty());
    }
}
