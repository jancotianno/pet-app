package com.example.petapp.mapper;

import com.example.petapp.dto.PetDto;
import com.example.petapp.model.Pet;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class PetMapperTest {

    @Autowired
    private PetMapper mapper;

    @Test
    void toDto_shouldMapAllFields() {
        Pet pet = new Pet(1L, "Fido", "Dog", 3, "Mario");

        PetDto dto = mapper.toDto(pet);

        assertThat(dto).isNotNull();
        assertThat(dto.id()).isEqualTo(1L);
        assertThat(dto.name()).isEqualTo("Fido");
        assertThat(dto.species()).isEqualTo("Dog");
        assertThat(dto.age()).isEqualTo(3);
        assertThat(dto.ownerName()).isEqualTo("Mario");
    }

    @Test
    void toEntity_shouldMapAllFields() {
        PetDto dto = new PetDto(1L, "Luna", "Cat", 2, "Anna");

        Pet pet = mapper.toEntity(dto);

        assertThat(pet).isNotNull();
        assertThat(pet.getId()).isEqualTo(1L);
        assertThat(pet.getName()).isEqualTo("Luna");
        assertThat(pet.getSpecies()).isEqualTo("Cat");
        assertThat(pet.getAge()).isEqualTo(2);
        assertThat(pet.getOwnerName()).isEqualTo("Anna");
    }
}
