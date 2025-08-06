package com.example.petapp.service;

import com.example.petapp.dto.PetDto;
import com.example.petapp.exception.PetNotFoundException;
import com.example.petapp.mapper.PetMapper;
import com.example.petapp.model.Pet;
import com.example.petapp.repository.PetRepository;

import java.util.List;
import java.util.stream.Collectors;

public class PetServiceImpl implements PetService {

    private final PetRepository petRepository;

    private final PetMapper petMapper;

    public PetServiceImpl(PetRepository petRepository, PetMapper petMapper) {
        this.petRepository = petRepository;
        this.petMapper = petMapper;
    }

    @Override
    public PetDto createPet(PetDto petDto) {
        Pet pet = petMapper.toEntity(petDto);
        Pet savedPet = petRepository.save(pet);
        return petMapper.toDto(savedPet);
    }

    @Override
    public PetDto getPetById(Long id) {
        Pet pet = petRepository.findById(id)
                .orElseThrow(() -> new PetNotFoundException( id));
        return petMapper.toDto(pet);
    }

    @Override
    public List<PetDto> getAllPets() {
        return petRepository.findAll().stream()
                .map(petMapper::toDto)
                .collect(Collectors.toList());    }

    @Override
    public PetDto updatePet(Long id, PetDto petDto) {
        Pet existingPet = petRepository.findById(id)
                .orElseThrow(() -> new PetNotFoundException(id));

        existingPet.setName(petDto.name());
        existingPet.setSpecies(petDto.species());
        existingPet.setAge(petDto.age());
        existingPet.setOwnerName(petDto.ownerName());

        Pet updated = petRepository.save(existingPet);
        return petMapper.toDto(updated);
    }

    @Override
    public void deletePet(Long id) {
        if (!petRepository.existsById(id)) {
            throw new PetNotFoundException(id);
        }
        petRepository.deleteById(id);
    }
}

