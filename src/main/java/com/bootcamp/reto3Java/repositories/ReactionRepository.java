package com.bootcamp.reto3Java.repositories;

import com.bootcamp.reto3Java.entities.Reaction;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReactionRepository extends ReactiveMongoRepository<Reaction, String> {
}
