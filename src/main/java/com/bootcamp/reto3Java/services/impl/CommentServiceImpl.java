package com.bootcamp.reto3Java.services.impl;

import com.bootcamp.reto3Java.entities.Comment;
import com.bootcamp.reto3Java.repositories.CommentRepository;
import com.bootcamp.reto3Java.services.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Override
    public Flux<Comment> findAll() {
        return commentRepository.findAll();
    }

    @Override
    public Mono<Comment> save(Comment request) {
        return commentRepository.save(request);
    }

    @Override
    public Mono<Void> deleteById(String id) {
        return commentRepository.deleteById(id);
    }

    @Override
    public Mono<Void> delete(Comment request) {
        return commentRepository.delete(request);
    }

    @Override
    public Mono<Comment> findById(String id) {
        return commentRepository.findById(id);
    }

    @Override
    public Mono<Comment> updateById(String id, Comment request) {
        return commentRepository.findById(id)
                .flatMap(comment -> {
                    comment.setDate(request.getDate() != null ? request.getDate() : comment.getDate());
                    comment.setEstado(request.getEstado() != null ? request.getEstado() : comment.getEstado());
                    comment.setComment(request.getComment() != null ? request.getComment() : comment.getComment());
                    comment.setUserId(request.getUserId() != null ? request.getUserId() : comment.getUserId());
                    comment.setPostId(request.getPostId() != null ? request.getPostId() : comment.getPostId());
                    return commentRepository.save(comment);
                })
                .switchIfEmpty(Mono.empty());
    }
}
