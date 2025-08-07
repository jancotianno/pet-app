package com.example.petapp.repository;

import com.example.petapp.model.Pet;
import org.springframework.context.annotation.Profile;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
@Profile("mongodb")
public interface MongoPetRepository extends MongoRepository<Pet, String>, PetRepository {
    // TODO: Implement any custom MongoDB specific methods if needed

    // Optional<Pet> findByName(String name);

    // List<Pet> findBySpecies(String species);

    // long countBySpecies(String species);

    // boolean existsByName(String name);

    //@Query("{ 'species': ?0, 'age': { $gt: ?1 } }")
    //List<Pet> findBySpeciesAndAgeGreaterThan(String species, int age);

}