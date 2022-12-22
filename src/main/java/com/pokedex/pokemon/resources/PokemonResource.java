package com.pokedex.pokemon.resources;

import com.pokedex.pokemon.entities.Pokemon;
import com.pokedex.pokemon.entities.PokemonRepository;
import com.pokedex.pokemon.exceptions.PokemonNotFoundException;
import java.util.List;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PokemonResource {
    
    private final PokemonRepository repository;
    
    PokemonResource(PokemonRepository repository) {
        this.repository = repository;
    }
    
    @GetMapping("/pokemons")
    List<Pokemon> all() {
        return repository.findAll();
    }
    
    @PostMapping("/pokemons")
    Pokemon newPokemon(@RequestBody Pokemon newPokemon) {
        return repository.save(newPokemon);
    }
    
    @GetMapping("/pokemons/{id}")
    Pokemon one(@PathVariable Long id) {
        return repository.findById(id).orElseThrow(() -> new PokemonNotFoundException(id));
    }
    
    @DeleteMapping("/pokemons/{id}")
    void deletePokemon(@PathVariable Long id) {
        repository.deleteById(id);
    }
}
