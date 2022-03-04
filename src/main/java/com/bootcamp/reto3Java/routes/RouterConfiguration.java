package com.bootcamp.reto3Java.routes;

import com.bootcamp.reto3Java.handlers.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
public class RouterConfiguration {

    @Bean
    public RouterFunction<ServerResponse> authorRoutes (AuthorHandler authorHandler) {
        return RouterFunctions.nest(RequestPredicates.path("/authors"),
                RouterFunctions
                        .route(RequestPredicates.GET(""), authorHandler::findAllAuthors)
                        .andRoute(RequestPredicates.POST(""), authorHandler::saveAuthor)
                        .andRoute(RequestPredicates.DELETE("/{id}"), authorHandler::deleteAuthor)
                        .andRoute(RequestPredicates.GET("/{id}"), authorHandler::findAuthorById)
                        .andRoute(RequestPredicates.PATCH("/{id}"), authorHandler::updateAuthorById)
        );
    }

    @Bean
    public RouterFunction<ServerResponse> userRoutes (UserHandler userHandler) {
        return RouterFunctions.nest(RequestPredicates.path("/users"),
                RouterFunctions
                        .route(RequestPredicates.GET(""), userHandler::findAllUsers)
                        .andRoute(RequestPredicates.POST(""), userHandler::saveUser)
                        .andRoute(RequestPredicates.DELETE("/{id}"), userHandler::deleteUser)
                        .andRoute(RequestPredicates.GET("/{id}"), userHandler::findUserById)
                        .andRoute(RequestPredicates.PATCH("/{id}"), userHandler::updateUserById)
                        .andRoute(RequestPredicates.POST("/login") , userHandler::logIn)
                        .andRoute(RequestPredicates.POST("/logoff") , userHandler::logOff)
        );
    }

    @Bean
    public RouterFunction<ServerResponse> blogRoutes (BlogHandler blogHandler) {
        return RouterFunctions.nest(RequestPredicates.path("/blogs"),
                RouterFunctions
                        .route(RequestPredicates.GET(""), blogHandler::findAllBlogs)
                        .andRoute(RequestPredicates.POST(""), blogHandler::saveBlog)
                        .andRoute(RequestPredicates.DELETE("/{id}"), blogHandler::deleteBlog)
                        .andRoute(RequestPredicates.GET("/{id}"), blogHandler::findBlogById)
                        .andRoute(RequestPredicates.PATCH("/{id}"), blogHandler::updateBlogById)
                        .andRoute(RequestPredicates.POST("/activate/{id}"), blogHandler::activate)
        );
    }

    @Bean
    public RouterFunction<ServerResponse> postRoutes (PostHandler postHandler) {
        return RouterFunctions.nest(RequestPredicates.path("/posts"),
                RouterFunctions
                        .route(RequestPredicates.GET(""), postHandler::findAllPosts)
                        .andRoute(RequestPredicates.POST(""), postHandler::savePost)
                        .andRoute(RequestPredicates.DELETE("/{id}"), postHandler::deletePost)
                        .andRoute(RequestPredicates.GET("/{id}"), postHandler::findPostById)
                        .andRoute(RequestPredicates.PATCH("/{id}"), postHandler::updatePostById)
                        .andRoute(RequestPredicates.POST("/publicate/{id}"), postHandler::publicate)
        );
    }

    @Bean
    public RouterFunction<ServerResponse> commentRoutes (CommentHandler commentHandler) {
        return RouterFunctions.nest(RequestPredicates.path("/comments"),
                RouterFunctions
                        .route(RequestPredicates.GET(""), commentHandler::findAllComments)
                        .andRoute(RequestPredicates.POST(""), commentHandler::saveComment)
                        .andRoute(RequestPredicates.DELETE("/{id}"), commentHandler::deleteComment)
                        .andRoute(RequestPredicates.GET("/{id}"), commentHandler::findCommentById)
                        .andRoute(RequestPredicates.PATCH("/{id}"), commentHandler::updateCommentById)
        );
    }

    @Bean
    public RouterFunction<ServerResponse> reactionRoutes (ReactionHandler reactionHandler) {
        return RouterFunctions.nest(RequestPredicates.path("/reactions"),
                RouterFunctions
                        .route(RequestPredicates.GET(""), reactionHandler::findAllReactions)
                        .andRoute(RequestPredicates.POST(""), reactionHandler::saveReaction)
                        .andRoute(RequestPredicates.DELETE("/{id}"), reactionHandler::deleteReaction)
                        .andRoute(RequestPredicates.GET("/{id}"), reactionHandler::findReactionById)
                        .andRoute(RequestPredicates.PATCH("/{id}"), reactionHandler::updateReactionById)
        );
    }
}
