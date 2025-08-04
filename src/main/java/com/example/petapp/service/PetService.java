package com.example.petapp.service;

import com.example.petapp.model.Pet;

import java.util.List;
import java.util.Optional;

public interface PetService {

    Pet createPet(Pet pet);
    Optional<Pet> getPetById(Long id);
    List<Pet> getAllPets();
    Pet updatePet(Long id, Pet pet);
    void deletePet(Long id);

}
