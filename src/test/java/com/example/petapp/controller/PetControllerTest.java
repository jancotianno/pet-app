package com.example.petapp.controller;

import com.example.petapp.exception.GlobalExceptionHandler;
import com.example.petapp.exception.PetNotFoundException;
import com.example.petapp.model.Pet;
import com.example.petapp.service.PetService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class PetControllerTest {

    private MockMvc mockMvc;

    @Mock
    private PetService petService;

    @InjectMocks
    private PetController petController;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(petController)
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();
    }

    @Test
    void getPet_existingId_returnsOk() throws Exception {
        Pet pet = new Pet(1L, "Fido", "Dog", 3, "Mario");

        when(petService.getPetById(1L)).thenReturn(pet);

        mockMvc.perform(get("/pets/1")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Fido"))
                .andExpect(jsonPath("$.species").value("Dog"));
    }

    @Test
    void getPet_notExistingId_returnsNotFound() throws Exception {
        when(petService.getPetById(99L)).thenThrow(new PetNotFoundException(99L));

        mockMvc.perform(get("/pets/99")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void createPet_validInput_returnsCreated() throws Exception {
        Pet pet = new Pet(null, "Fido", "Dog", 3, "Mario");
        Pet savedPet = new Pet(1L, "Fido", "Dog", 3, "Mario");

        when(petService.createPet(pet)).thenReturn(savedPet);

        mockMvc.perform(post("/pets")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(pet)))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", "/pets/1"))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Fido"));
    }

    @Test
    void updatePet_existingId_returnsOk() throws Exception {
        Pet petUpdate = new Pet(null, "Fido", "Dog", 4, "Mario");
        Pet updatedPet = new Pet(1L, "Fido", "Dog", 4, "Mario");

        when(petService.updatePet(eq(1L), any(Pet.class))).thenReturn(updatedPet);

        mockMvc.perform(put("/pets/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(petUpdate)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.age").value(4));
    }

    @Test
    void updatePet_notExistingId_returnsNotFound() throws Exception {
        Pet petUpdate = new Pet(null, "Fido", "Dog", 4, "Mario");

        when(petService.updatePet(eq(99L), any(Pet.class))).thenThrow(new PetNotFoundException(99L));

        mockMvc.perform(put("/pets/99")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(petUpdate)))
                .andExpect(status().isNotFound());
    }

    // Test DELETE /pets/{id}
    @Test
    void deletePet_existingId_returnsNoContent() throws Exception {
        // Non serve stub, se il metodo non lancia eccezioni va bene

        mockMvc.perform(delete("/pets/1"))
                .andExpect(status().isNoContent());

        verify(petService).deletePet(1L);
    }

    @Test
    void deletePet_notExistingId_returnsNotFound() throws Exception {
        doThrow(new PetNotFoundException(99L)).when(petService).deletePet(99L);

        mockMvc.perform(delete("/pets/99"))
                .andExpect(status().isNotFound());
    }
}
