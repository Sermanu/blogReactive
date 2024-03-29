package com.bootcamp.reto3Java.repositories;

import com.bootcamp.reto3Java.entities.Comment;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends ReactiveMongoRepository<Comment, String> {
}
