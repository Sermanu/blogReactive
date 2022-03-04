package com.bootcamp.reto3Java.handlers;


import com.bootcamp.reto3Java.entities.Reaction;
import com.bootcamp.reto3Java.services.ReactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
public class ReactionHandler {

    @Autowired
    private ReactionService reactionService;

    public Mono<ServerResponse> findAllReactions(ServerRequest request) {
        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
                .body(reactionService.findAll(), Reaction.class);
    }

    public Mono<ServerResponse> saveReaction(ServerRequest request) {
        return request.bodyToMono(Reaction.class)
                .flatMap(reaction ->
                        reactionService
                                .findAll()
                                .filter(reaction1 -> reaction1.getUserId().equals(reaction.getUserId()))
                                .collectList()
                                .flatMap(reactions -> {
                                    if (reactions.size() == 0) {
                                        return ServerResponse.ok()
                                                .contentType(MediaType.APPLICATION_JSON)
                                                .body(Mono.just(reaction), Reaction.class);
                                    } else {
                                        return ServerResponse.status(HttpStatus.PRECONDITION_FAILED).build();
                                    }
                                })
                );
    }

    public Mono<ServerResponse> deleteReaction(ServerRequest request) {
        String reactionId = request.pathVariable("id");
        return reactionService.findById(reactionId)
                .flatMap(reaction -> reactionService.delete(reaction).then(ServerResponse.noContent().build()))
                .switchIfEmpty(ServerResponse.notFound().build());
    }

    public Mono<ServerResponse> findReactionById(ServerRequest request) {
        String reactionId = request.pathVariable("id");
        return reactionService
                .findById(reactionId)
                .flatMap(reaction -> ServerResponse.ok().body(Mono.just(reaction), Reaction.class))
                .switchIfEmpty(ServerResponse.notFound().build());
    }

    public Mono<ServerResponse> updateReactionById(ServerRequest request) {
        String reactionId = request.pathVariable("id");
        return request.bodyToMono(Reaction.class)
                .flatMap(reactionRequest -> {
                    return reactionService.updateById(reactionId, reactionRequest)
                            .flatMap(reactionShow -> ServerResponse.ok().body(Mono.just(reactionShow), Reaction.class))
                            .switchIfEmpty(ServerResponse.notFound().build());
                });

    }

}
