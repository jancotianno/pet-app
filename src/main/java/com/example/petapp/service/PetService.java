package com.example.petapp.service;

import com.example.petapp.model.Pet;

import java.util.List;

public interface PetService {

    Pet createPet(Pet pet);
    Pet getPetById(Long id);
    List<Pet> getAllPets();
    Pet updatePet(Long id, Pet pet);
    void deletePet(Long id);

}
