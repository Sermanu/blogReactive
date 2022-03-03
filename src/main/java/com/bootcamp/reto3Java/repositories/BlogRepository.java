package com.bootcamp.reto3Java.repositories;

import com.bootcamp.reto3Java.entities.Blog;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BlogRepository extends ReactiveMongoRepository<Blog, String> {
}
