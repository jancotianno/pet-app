package com.example.petapp.service;

import com.example.petapp.exception.PetNotFoundException;
import com.example.petapp.model.Pet;
import com.example.petapp.repository.PetRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

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

    @InjectMocks
    private PetServiceImpl petService;

    @Test
    void whenUpdateExistingPet_thenReturnsUpdated() {
        Pet existing = new Pet(1L, "Fido", "Dog", 5, "Alice");
        Pet updated = new Pet(null, "Fido2", "Dog", 6, "Alice");

        when(petRepository.findById(1L)).thenReturn(Optional.of(existing));
        when(petRepository.save(any(Pet.class))).thenAnswer(i -> i.getArgument(0));

        Pet result = petService.updatePet(1L, updated);
        assertEquals("Fido2", result.getName());
        verify(petRepository).save(existing);
    }

    @Test
    void whenDeleteNonExisting_thenThrows() {
        when(petRepository.existsById(99L)).thenReturn(false);
        assertThrows(PetNotFoundException.class, () -> petService.deletePet(99L));
    }
}

