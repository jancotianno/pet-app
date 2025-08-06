package com.example.petapp.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public record PetDto(@NotBlank String name,
                     @NotBlank String species,
                     @Min(0)Integer age,
                     @NotBlank String ownerName) {}
