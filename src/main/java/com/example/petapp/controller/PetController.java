package com.example.petapp.controller;

import com.example.petapp.dto.PetDto;
import com.example.petapp.service.PetService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pets")
public class PetController {

    private final PetService petService;

    public PetController(PetService petService) {
        this.petService = petService;
    }

    @PostMapping
    public ResponseEntity<PetDto> createPet(@RequestBody @Valid PetDto petDto) {
        PetDto createdPet = petService.createPet(petDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdPet);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PetDto> getPet(@PathVariable Long id) {
        PetDto petDto = petService.getPetById(id);
        return ResponseEntity.ok(petDto);
    }

    @GetMapping
    public ResponseEntity<List<PetDto>> getAllPets() {
        return ResponseEntity.ok(petService.getAllPets());
    }

    @PutMapping("/{id}")
    public ResponseEntity<PetDto> updatePet(@PathVariable Long id, @RequestBody @Valid PetDto  petDto) {
        return ResponseEntity.ok(petService.updatePet(id, petDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePet(@PathVariable Long id) {
        petService.deletePet(id);
        return ResponseEntity.noContent().build();
    }
}

