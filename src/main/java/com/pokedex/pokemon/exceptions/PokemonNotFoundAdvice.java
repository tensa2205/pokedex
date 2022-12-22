package com.pokedex.pokemon.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/*
    Class that can handle specific exceptions and respond accordingly
*/
@ControllerAdvice
public class PokemonNotFoundAdvice {
    
    @ResponseBody
    @ExceptionHandler(PokemonNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String pokemonNotFoundHandler(PokemonNotFoundException ex) {
        return ex.getMessage();
    }
}
