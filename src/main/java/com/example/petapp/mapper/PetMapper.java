package com.example.petapp.mapper;

import com.example.petapp.dto.PetDto;
import com.example.petapp.model.Pet;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PetMapper {

    PetDto toDto(Pet pet);

    Pet toEntity(PetDto petDto);
}

