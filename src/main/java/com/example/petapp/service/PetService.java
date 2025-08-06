package com.example.petapp.service;

import com.example.petapp.dto.PetDto;

import java.util.List;

public interface PetService {

    PetDto createPet(PetDto petDto);
    PetDto getPetById(Long id);
    List<PetDto> getAllPets();
    PetDto updatePet(Long id, PetDto petDto);
    void deletePet(Long id);

}
