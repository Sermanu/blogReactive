package com.bootcamp.reto3Java.handlers;

import com.bootcamp.reto3Java.entities.Comment;
import com.bootcamp.reto3Java.services.CommentService;
import com.bootcamp.reto3Java.services.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
public class CommentHandler {

    @Autowired
    private CommentService commentService;

    @Autowired
    private PostService postService;

    public Mono<ServerResponse> findAllComments(ServerRequest request) {
        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
                .body(commentService.findAll(), Comment.class);
    }

    public Mono<ServerResponse> saveComment(ServerRequest request) {
        return request.bodyToMono(Comment.class)
                .flatMap(comment -> postService
                        .findById(comment.getPostId())
                        .flatMap(post -> {
                            if (post.getStatus().equals("publicado")) {
                                return ServerResponse.ok().body(commentService.save(comment), Comment.class);
                            } else {
                                return ServerResponse.status(HttpStatus.PRECONDITION_REQUIRED).build();
                            }
                        }));
    }

    public Mono<ServerResponse> deleteComment(ServerRequest request) {
        String commentId = request.pathVariable("id");
        return commentService.findById(commentId)
                .flatMap(comment -> commentService.delete(comment).then(ServerResponse.noContent().build()))
                .switchIfEmpty(ServerResponse.notFound().build());
    }

    public Mono<ServerResponse> findCommentById(ServerRequest request) {
        String commentId = request.pathVariable("id");
        return commentService
                .findById(commentId)
                .flatMap(comment -> ServerResponse.ok().body(Mono.just(comment), Comment.class))
                .switchIfEmpty(ServerResponse.notFound().build());
    }

    public Mono<ServerResponse> updateCommentById(ServerRequest request) {
        String commentId = request.pathVariable("id");
        return request.bodyToMono(Comment.class)
                .flatMap(commentRequest -> {
                    return commentService.updateById(commentId, commentRequest)
                            .flatMap(commentShow -> ServerResponse.ok().body(Mono.just(commentShow), Comment.class))
                            .switchIfEmpty(ServerResponse.notFound().build());
                });

    }

}
