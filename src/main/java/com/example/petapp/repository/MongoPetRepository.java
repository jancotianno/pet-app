package com.example.petapp.repository;

import com.example.petapp.model.Pet;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

@Repository
@Profile("mongodb")
public interface MongoPetRepository extends MongoRepository<Pet, String>, PetRepository {
    // TODO: Implement any custom MongoDB specific methods if needed
}