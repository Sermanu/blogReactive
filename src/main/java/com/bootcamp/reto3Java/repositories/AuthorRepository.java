package com.bootcamp.reto3Java.repositories;

import com.bootcamp.reto3Java.entities.Author;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorRepository extends ReactiveMongoRepository<Author, String> {
}