package com.example.petapp.repository;

import com.example.petapp.model.Pet;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

@Repository
@Profile("in-memory")
public class InMemoryPetRepository implements PetRepository {
    private final Map<Long, Pet> db = new HashMap<>();
    private final AtomicLong idGen = new AtomicLong();

    public Pet save(Pet pet) {
        if (pet.getId() == null) pet.setId(idGen.incrementAndGet());
        db.put(pet.getId(), pet);
        return pet;
    }

    public Optional<Pet> findById(Long id) { return Optional.ofNullable(db.get(id)); }
    public List<Pet> findAll() { return new ArrayList<>(db.values()); }
    public void deleteById(Long id) { db.remove(id); }
    public boolean existsById(Long id) { return db.containsKey(id); }
}

