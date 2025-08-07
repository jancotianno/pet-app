package com.example.petapp.service;

import com.example.petapp.dto.PetDto;
import com.example.petapp.exception.PetNotFoundException;
import com.example.petapp.mapper.PetMapper;
import com.example.petapp.model.Pet;
import com.example.petapp.repository.PetRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class PetServiceImpl implements PetService {

    private final PetRepository petRepository;

    private final PetMapper petMapper;

    public PetServiceImpl(PetRepository petRepository, PetMapper petMapper) {
        this.petRepository = petRepository;
        this.petMapper = petMapper;
    }

    @Override
    public PetDto createPet(PetDto petDto) {
        log.info("Creating new Pet: {}", petDto);
        Pet pet = petMapper.toEntity(petDto);
        Pet savedPet = petRepository.save(pet);
        log.info("Pet created with ID: {}", savedPet.getId());
        return petMapper.toDto(savedPet);
    }

    @Override
    public PetDto getPetById(Long id) {
        log.info("Fetching Pet by id: {}", id);
        Pet pet = petRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Pet not found with id: {}", id);
                    return new PetNotFoundException(id);
                });
        log.info("Pet found: {}", pet);
        return petMapper.toDto(pet);
    }

    @Override
    public List<PetDto> getAllPets() {
        log.info("Fetching all pets");
        List<PetDto> pets = petRepository.findAll().stream()
                .map(petMapper::toDto)
                .collect(Collectors.toList());
        log.info("Number of pets found: {}", pets.size());
        return pets;
    }

    @Override
    public PetDto updatePet(Long id, PetDto petDto) {
        log.info("Updating Pet with id: {}, new data: {}", id, petDto);
        Pet existingPet = petRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Pet not found for update with id: {}", id);
                    return new PetNotFoundException(id);
                });

        existingPet.setName(petDto.name());
        existingPet.setSpecies(petDto.species());
        existingPet.setAge(petDto.age());
        existingPet.setOwnerName(petDto.ownerName());

        Pet updated = petRepository.save(existingPet);
        log.info("Pet updated with id: {}", updated.getId());
        return petMapper.toDto(updated);
    }

    @Override
    public void deletePet(Long id) {
        log.info("Deleting Pet with id: {}", id);
        if (!petRepository.existsById(id)) {
            log.error("Pet not found for delete with id: {}", id);
            throw new PetNotFoundException(id);
        }
        petRepository.deleteById(id);
        log.info("Pet deleted with id: {}", id);
    }
}

