package com.example.petapp.controller;

import com.example.petapp.dto.PetDto;
import com.example.petapp.exception.PetNotFoundException;
import com.example.petapp.security.SecurityConfig;
import com.example.petapp.service.PetService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PetController.class)
@Import(SecurityConfig.class)
class PetControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PetService petService;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void getPet_existingId_returnsOk() throws Exception {
        PetDto petDto = new PetDto(1L, "Fido", "Dog", 3, "Mario");

        when(petService.getPetById(1L)).thenReturn(petDto);

        mockMvc.perform(get("/pets/1")
                        .with(httpBasic("user", "password"))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(petDto.id()))
                .andExpect(jsonPath("$.name").value("Fido"))
                .andExpect(jsonPath("$.species").value("Dog"));
    }

    @Test
    void getPet_notExistingId_returnsNotFound() throws Exception {
        when(petService.getPetById(99L)).thenThrow(new PetNotFoundException(99L));

        mockMvc.perform(get("/pets/99")
                        .with(httpBasic("user", "password"))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void createPet_validInput_returnsCreated() throws Exception {
        PetDto petDto = new PetDto(null, "Fido", "Dog", 3, "Mario");
        PetDto savedPet = new PetDto(1L, "Fido", "Dog", 3, "Mario");

        when(petService.createPet(petDto)).thenReturn(savedPet);

        mockMvc.perform(post("/pets")
                        .with(httpBasic("user", "password"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(petDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(savedPet.id()))
                .andExpect(jsonPath("$.species").value("Dog"))
                .andExpect(jsonPath("$.name").value("Fido"));
    }

    @Test
    void updatePet_existingId_returnsOk() throws Exception {
        PetDto petUpdate = new PetDto( 1L, "Fido", "Dog", 4, "Mario");
        PetDto updatedPet = new PetDto( 1L,"Fido", "Dog", 4, "Mario");

        when(petService.updatePet(eq(1L), any(PetDto.class))).thenReturn(updatedPet);

        mockMvc.perform(put("/pets/1")
                        .with(httpBasic("user", "password"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(petUpdate)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.age").value(4));
    }

    @Test
    void updatePet_notExistingId_returnsNotFound() throws Exception {
        PetDto petUpdate = new PetDto( 99L, "Fido", "Dog", 4, "Mario");

        when(petService.updatePet(eq(99L), any(PetDto.class)))
                .thenThrow(new PetNotFoundException(99L));

        mockMvc.perform(put("/pets/99")
                        .with(httpBasic("user", "password"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(petUpdate)))
                .andExpect(status().isNotFound());
    }

    @Test
    void deletePet_existingId_returnsNoContent() throws Exception {
        mockMvc.perform(delete("/pets/1")
                        .with(httpBasic("user", "password"))
                )
                .andExpect(status().isNoContent());

        verify(petService).deletePet(1L);
    }

    @Test
    void deletePet_notExistingId_returnsNotFound() throws Exception {
        doThrow(new PetNotFoundException(99L)).when(petService).deletePet(99L);

        mockMvc.perform(delete("/pets/99")
                        .with(httpBasic("user", "password"))
                )
                .andExpect(status().isNotFound());
    }

    @Test
    void getPet_withInvalidCredentials_returnsUnauthorized() throws Exception {
        mockMvc.perform(get("/pets/1")
                        .with(httpBasic("wrongUser", "wrongPassword"))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized());
    }
}