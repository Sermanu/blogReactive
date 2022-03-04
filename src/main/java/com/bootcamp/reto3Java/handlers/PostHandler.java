package com.bootcamp.reto3Java.handlers;

import com.bootcamp.reto3Java.entities.Post;
import com.bootcamp.reto3Java.services.BlogService;
import com.bootcamp.reto3Java.services.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.Date;
import java.util.concurrent.TimeUnit;

@Component
public class PostHandler {

    @Autowired
    private PostService postService;

    @Autowired
    private BlogService blogService;

    public Mono<ServerResponse> findAllPosts(ServerRequest request) {
        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
                .body(postService.findAll(), Post.class);
    }

    public Mono<ServerResponse> savePost(ServerRequest request) {

        return request.bodyToMono(Post.class)
                // Buscar Blog asociado
                .flatMap(post -> blogService
                        .findById(post.getBlogId())
                        .flatMap(blog -> {
                            // Validacion de solo post en blog con estado activo
                            if (blog.getStatus().equals("activo")) {

                                return postService
                                        .findAll()
                                        .filter(postFilter -> postFilter.getBlogId().equals(post.getBlogId()))
                                        .collectList()
                                        .flatMap(posts -> {
                                            // Validacion de un solo post de blog por dia
                                            if (posts.size() == 0) {
                                                return ServerResponse.ok().body(postService.save(post), Post.class);
                                            } else {
                                                boolean canSavePost = true;

                                                for (Post postItem : posts) {


                                                    Date dateOfPostSaved =postItem.getDate();
                                                    Date dateOfPostToSave = post.getDate();

                                                    long diffInMillies = Math.abs(dateOfPostToSave.getTime() - dateOfPostSaved.getTime());
                                                    long diff = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);

                                                    if (diff < 1) {
                                                        canSavePost = false;
                                                        break;
                                                    }

                                                }

                                                if (canSavePost) {
                                                    return ServerResponse.ok().body(postService.save(post), Post.class);
                                                } else {
                                                    return ServerResponse.status(HttpStatus.PRECONDITION_FAILED).build();
                                                }

                                            }
                                        });
                            } else {
                                return ServerResponse.status(HttpStatus.PRECONDITION_REQUIRED).build();
                            }
                        })
                        .switchIfEmpty(ServerResponse.notFound().build()));

    }

    public Mono<ServerResponse> deletePost(ServerRequest request) {
        String postId = request.pathVariable("id");
        return postService.findById(postId)
                .flatMap(post -> postService.delete(post).then(ServerResponse.noContent().build())
                        .switchIfEmpty(ServerResponse.notFound().build()));
    }

    public Mono<ServerResponse> findPostById(ServerRequest request) {
        String postId = request.pathVariable("id");
        return postService
                .findById(postId)
                .flatMap(post -> ServerResponse.ok().body(Mono.just(post), Post.class))
                .switchIfEmpty(ServerResponse.notFound().build());
    }

    public Mono<ServerResponse> updatePostById(ServerRequest request) {
        String postId = request.pathVariable("id");
        return request.bodyToMono(Post.class)
                .flatMap(postRequest -> {
                    return postService.updateById(postId, postRequest)
                            .flatMap(postShow -> ServerResponse.ok().body(Mono.just(postShow), Post.class))
                            .switchIfEmpty(ServerResponse.notFound().build());
                });

    }

    public Mono<ServerResponse> publicate(ServerRequest request) {
        String postId = request.pathVariable("id");
        return postService.publicatePost(postId)
                .flatMap(postPublicate -> ServerResponse.ok().body(Mono.just(postPublicate), Post.class))
                .switchIfEmpty(ServerResponse.notFound().build());
    }

}
