package com.example.petapp.dto;

import lombok.Data;

@Data
public class PetDto {

    private String name;
    private String species;
    private Integer age;
    private String ownerName;

}
