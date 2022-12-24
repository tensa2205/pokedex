package com.pokedex.pokemon.resources;

import com.pokedex.pokemon.services.AdminService;
import java.util.List;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AdminResource {
    private final AdminService adminService;
    
    AdminResource(AdminService adminService) {
        this.adminService = adminService;
    }
    
    @PostMapping("/generate-pokemons")
    void generatePokemons() {
        adminService.generatePokemons();
    }
    
    @GetMapping("/retrieve-pokemons")
    @CrossOrigin(origins = {"http://localhost:3000"}) //Allow React server requests
    @ResponseBody
    List<String> retrievePokemons() {
        return adminService.getPokemonsFromDB();
    }
}
