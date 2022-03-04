package com.bootcamp.reto3Java.services.impl;

import com.bootcamp.reto3Java.entities.Reaction;
import com.bootcamp.reto3Java.repositories.ReactionRepository;
import com.bootcamp.reto3Java.services.ReactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class ReactionServiceImpl implements ReactionService {

    @Autowired
    private ReactionRepository reactionRepository;

    @Override
    public Flux<Reaction> findAll() {
        return reactionRepository.findAll();
    }

    @Override
    public Mono<Reaction> save(Reaction request) {
        request.setType("like");
        return reactionRepository.save(request);
    }

    @Override
    public Mono<Void> deleteById(String id) {
        return reactionRepository.deleteById(id);
    }

    @Override
    public Mono<Void> delete(Reaction request) {
        return reactionRepository.delete(request);
    }

    @Override
    public Mono<Reaction> findById(String id) {
        return reactionRepository.findById(id);
    }

    @Override
    public Mono<Reaction> updateById(String id, Reaction request) {
        return reactionRepository.findById(id)
                .flatMap(reaction -> {
                    reaction.setType(request.getType() != null ? request.getType() : reaction.getType());
                    reaction.setDate(request.getDate() != null ? request.getDate() : reaction.getDate());
                    reaction.setUserId(request.getUserId() != null ? request.getUserId() : reaction.getUserId());
                    reaction.setPostId(request.getPostId() != null ? request.getPostId() : reaction.getPostId());;

                    return reactionRepository.save(reaction);
                })
                .switchIfEmpty(Mono.empty());
    }
}
