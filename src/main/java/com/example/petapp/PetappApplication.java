package com.example.petapp;

import com.example.petapp.mapper.PetMapper;
import com.example.petapp.repository.PetRepository;
import com.example.petapp.service.PetService;
import com.example.petapp.service.PetServiceImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class PetappApplication {

	public static void main(String[] args) {
		SpringApplication.run(PetappApplication.class, args);
	}

    @Bean
    public PetService petService(PetRepository petRepository, PetMapper petMapper) {
        return new PetServiceImpl(petRepository, petMapper);
    }
}
