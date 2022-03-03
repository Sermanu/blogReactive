package com.bootcamp.reto3Java.routes;

import com.bootcamp.reto3Java.handlers.AuthorHandler;
import com.bootcamp.reto3Java.handlers.BlogHandler;
import com.bootcamp.reto3Java.handlers.PostHandler;
import com.bootcamp.reto3Java.handlers.UserHandler;
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
                        .route(RequestPredicates.GET(""), authorHandler::findAll)
                        .andRoute(RequestPredicates.POST(""), authorHandler::save)
                        .andRoute(RequestPredicates.DELETE("/{id}"), authorHandler::delete)
                        .andRoute(RequestPredicates.GET("/{id}"), authorHandler::findById)
                        .andRoute(RequestPredicates.PATCH("/{id}"), authorHandler::updateById)
        );
    }

    @Bean
    public RouterFunction<ServerResponse> userRoutes (UserHandler userHandler) {
        return RouterFunctions.nest(RequestPredicates.path("/users"),
                RouterFunctions
                        .route(RequestPredicates.GET(""), userHandler::findAll)
                        .andRoute(RequestPredicates.POST(""), userHandler::save)
                        .andRoute(RequestPredicates.DELETE("/{id}"), userHandler::delete)
                        .andRoute(RequestPredicates.GET("/{id}"), userHandler::findById)
                        .andRoute(RequestPredicates.PATCH("/{id}"), userHandler::updateById)
                        .andRoute(RequestPredicates.POST("/login") , userHandler::logIn)
                        .andRoute(RequestPredicates.POST("/logoff") , userHandler::logOff)
        );
    }

    @Bean
    public RouterFunction<ServerResponse> blogRoutes (BlogHandler blogHandler) {
        return RouterFunctions.nest(RequestPredicates.path("/blogs"),
                RouterFunctions
                        .route(RequestPredicates.GET(""), blogHandler::findAll)
                        .andRoute(RequestPredicates.POST(""), blogHandler::save)
                        .andRoute(RequestPredicates.DELETE("/{id}"), blogHandler::delete)
                        .andRoute(RequestPredicates.GET("/{id}"), blogHandler::findById)
                        .andRoute(RequestPredicates.PATCH("/{id}"), blogHandler::updateById)
                        .andRoute(RequestPredicates.POST("/activate/{id}"), blogHandler::activate)
        );
    }

    @Bean
    public RouterFunction<ServerResponse> postRoutes (PostHandler postHandler) {
        return RouterFunctions.nest(RequestPredicates.path("/posts"),
                RouterFunctions
                        .route(RequestPredicates.GET(""), postHandler::findAll)
                        .andRoute(RequestPredicates.POST(""), postHandler::save)
                        .andRoute(RequestPredicates.DELETE("/{id}"), postHandler::delete)
                        .andRoute(RequestPredicates.GET("/{id}"), postHandler::findById)
                        .andRoute(RequestPredicates.PATCH("/{id}"), postHandler::updateById)
                        .andRoute(RequestPredicates.POST("/public/{id}"), postHandler::publicate)
        );
    }
}