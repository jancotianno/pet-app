package com.example.petapp.controller;

import com.example.petapp.dto.PetDto;
import com.example.petapp.service.PetService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/pets")
public class PetController {

    private final PetService petService;

    public PetController(PetService petService) {
        this.petService = petService;
    }

    @PostMapping
    public ResponseEntity<PetDto> createPet(@RequestBody @Valid PetDto petDto) {
        log.info("Request to create Pet: {}", petDto);
        PetDto createdPet = petService.createPet(petDto);
        log.info("Pet created with ID: {}", createdPet.id());
        return ResponseEntity.status(HttpStatus.CREATED).body(createdPet);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PetDto> getPet(@PathVariable Long id) {
        log.info("Request to get Pet with id: {}", id);
        PetDto petDto = petService.getPetById(id);
        log.info("Pet retrieved: {}", petDto);
        return ResponseEntity.ok(petDto);
    }

    @GetMapping
    public ResponseEntity<List<PetDto>> getAllPets() {
        log.info("Request to get all pets");
        List<PetDto> pets = petService.getAllPets();
        log.info("Number of pets retrieved: {}", pets.size());
        return ResponseEntity.ok(pets);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PetDto> updatePet(@PathVariable Long id, @RequestBody @Valid PetDto petDto) {
        log.info("Request to update Pet with id: {}, new data: {}", id, petDto);
        PetDto updatedPet = petService.updatePet(id, petDto);
        log.info("Pet updated: {}", updatedPet);
        return ResponseEntity.ok(updatedPet);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePet(@PathVariable Long id) {
        log.info("Request to delete Pet with id: {}", id);
        petService.deletePet(id);
        log.info("Pet deleted with id: {}", id);
        return ResponseEntity.noContent().build();
    }
}

