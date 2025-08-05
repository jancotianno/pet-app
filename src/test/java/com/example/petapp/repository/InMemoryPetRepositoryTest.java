package com.example.petapp.repository;

import com.example.petapp.model.Pet;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryPetRepositoryTest {

    private InMemoryPetRepository repository;

    @BeforeEach
    void setUp() {
        repository = new InMemoryPetRepository();
    }

    @Test
    void saveAndFindPet_success() {
        Pet pet = new Pet(null, "Luna", "Cat", 3, "Alice");
        Pet saved = repository.save(pet);
        assertNotNull(saved.getId());

        Optional<Pet> found = repository.findById(saved.getId());
        assertTrue(found.isPresent());
        assertEquals("Luna", found.get().getName());
    }

    @Test
    void deletePet_success() {
        Pet pet = new Pet(null, "Rex", "Dog", 5, "Bob");
        Pet saved = repository.save(pet);

        repository.deleteById(saved.getId());
        assertFalse(repository.findById(saved.getId()).isPresent());
    }

    @Test
    void existsById_returnsCorrectResult() {
        Pet pet = new Pet(null, "Zoe", "Rabbit", 2, null);
        Pet saved = repository.save(pet);

        assertTrue(repository.existsById(saved.getId()));
        assertFalse(repository.existsById(999L));
    }

    @Test
    void findAll_returnsAllPets() {
        repository.save(new Pet(null, "A", "Cat", 1, null));
        repository.save(new Pet(null, "B", "Dog", 2, null));

        List<Pet> pets = repository.findAll();
        assertEquals(2, pets.size());
    }
}

