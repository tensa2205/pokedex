package com.pokedex.pokemon.exceptions;

public class PokemonNotFoundException extends RuntimeException {
    public PokemonNotFoundException(Long id) {
        super("Could not find pokemon with id:" + id);
    }
}
