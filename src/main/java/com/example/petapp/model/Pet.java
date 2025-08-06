package com.example.petapp.model;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Pet {

    private Long id;

    @NotBlank(message = "Name is required")
    @Size(max = 100, message = "Name must be at most 100 characters")
    private String name;

    @NotBlank(message = "Species is required")
    @Size(max = 50, message = "Species must be at most 50 characters")
    private String species;

    @Min(value = 0, message = "Age must be zero or positive")
    private Integer age;

    @Size(max = 100, message = "Owner name must be at most 100 characters")
    private String ownerName;
}
