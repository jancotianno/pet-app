package com.example.petapp.service;

import com.example.petapp.exception.PetNotFoundException;
import com.example.petapp.model.Pet;
import com.example.petapp.repository.PetRepository;

import java.util.List;

public class PetServiceImpl implements PetService {

    private final PetRepository petRepository;

    public PetServiceImpl(PetRepository petRepository) {
        this.petRepository = petRepository;
    }

    @Override
    public Pet createPet(Pet pet) {
        return petRepository.save(pet);
    }

    @Override
    public Pet getPetById(Long id) {
        return petRepository.findById(id)
                .orElseThrow(() -> new PetNotFoundException(id));
    }

    @Override
    public List<Pet> getAllPets() {
        return petRepository.findAll();
    }

    @Override
    public Pet updatePet(Long id, Pet updatedData) {
        Pet existingPet = petRepository.findById(id)
                .orElseThrow(() -> new PetNotFoundException(id));

        existingPet.setName(updatedData.getName());
        existingPet.setSpecies(updatedData.getSpecies());
        existingPet.setAge(updatedData.getAge());
        existingPet.setOwnerName(updatedData.getOwnerName());

        return petRepository.save(existingPet);
    }

    @Override
    public void deletePet(Long id) {
        if (!petRepository.existsById(id)) {
            throw new PetNotFoundException(id);
        }
        petRepository.deleteById(id);
    }
}

