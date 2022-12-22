package com.pokedex.pokemon.entities;

import org.springframework.data.jpa.repository.JpaRepository;


public interface PokemonRepository extends JpaRepository<Pokemon, Long> {
    
}
