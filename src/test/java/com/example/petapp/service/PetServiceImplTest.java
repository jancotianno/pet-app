package com.example.petapp.service;

import com.example.petapp.dto.PetDto;
import com.example.petapp.exception.PetNotFoundException;
import com.example.petapp.mapper.PetMapper;
import com.example.petapp.model.Pet;
import com.example.petapp.repository.PetRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PetServiceImplTest {

    @Mock
    private PetRepository petRepository;

    @Mock
    private PetMapper petMapper;

    @InjectMocks
    private PetServiceImpl petService;

    @Test
    void whenUpdateExistingPet_thenReturnsUpdatedDto() {
        Pet existingPet = new Pet(1L, "Fido", "Dog", 5, "Alice");
        PetDto updatedDto = new PetDto("Fido2", "Dog", 6, "Alice");
        Pet updatedPet = new Pet(1L, "Fido2", "Dog", 6, "Alice");

        when(petRepository.findById(1L)).thenReturn(Optional.of(existingPet));
        when(petRepository.save(any())).thenReturn(updatedPet);
        when(petMapper.toDto(updatedPet)).thenReturn(updatedDto);

        PetDto result = petService.updatePet(1L, updatedDto);

        assertEquals("Fido2", result.name());
        verify(petRepository).save(any(Pet.class));
    }

    @Test
    void whenDeleteNonExisting_thenThrows() {
        when(petRepository.existsById(99L)).thenReturn(false);

        assertThrows(PetNotFoundException.class, () -> petService.deletePet(99L));
    }

    @Test
    void whenCreatePet_thenReturnsSavedDto() {
        PetDto inputDto = new PetDto("Fido", "Dog", 3, "Mario");
        Pet petToSave = new Pet(null, "Fido", "Dog", 3, "Mario");
        Pet savedPet = new Pet(1L, "Fido", "Dog", 3, "Mario");
        PetDto savedDto = new PetDto("Fido", "Dog", 3, "Mario");

        when(petMapper.toEntity(inputDto)).thenReturn(petToSave);
        when(petRepository.save(petToSave)).thenReturn(savedPet);
        when(petMapper.toDto(savedPet)).thenReturn(savedDto);

        PetDto result = petService.createPet(inputDto);

        assertEquals(savedDto, result);
    }

    @Test
    void whenFindAll_thenReturnsDtoList() {
        List<Pet> pets = List.of(
                new Pet(1L, "Fido", "Dog", 3, "Mario"),
                new Pet(2L, "Milo", "Cat", 2, "Giulia")
        );

        List<PetDto> petDtos = List.of(
                new PetDto("Fido", "Dog", 3, "Mario"),
                new PetDto("Milo", "Cat", 2, "Giulia")
        );

        when(petRepository.findAll()).thenReturn(pets);
        when(petMapper.toDto(pets.get(0))).thenReturn(petDtos.get(0));
        when(petMapper.toDto(pets.get(1))).thenReturn(petDtos.get(1));

        List<PetDto> result = petService.getAllPets();

        assertEquals(petDtos, result);
    }
}