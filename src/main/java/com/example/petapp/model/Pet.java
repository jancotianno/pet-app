package com.example.petapp.model;

import lombok.Data;

@Data
public class Pet {

    private Long id;
    private String name;
    private String species;
    private Integer age;
    private String ownerName;
}
