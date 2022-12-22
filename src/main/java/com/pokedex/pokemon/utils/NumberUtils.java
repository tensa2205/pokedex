/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.pokedex.pokemon.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import org.springframework.data.util.Pair;

/**
 *
 * @author Borla
 */
public class NumberUtils {
    private static final Integer NUMBER_OF_POKEMONS = 905;
    
    public static List<Pair<Integer,Integer>> getPokemonRanges() {
        List<Pair<Integer, Integer>> pairs = new ArrayList<>();
        Integer pokemonsPerProcessor = NUMBER_OF_POKEMONS / Runtime.getRuntime().availableProcessors();
        for (int i = 1; i < NUMBER_OF_POKEMONS; i+= pokemonsPerProcessor) {
            Integer from = i, to;
            if (i + pokemonsPerProcessor > NUMBER_OF_POKEMONS) {
                to = i + (NUMBER_OF_POKEMONS - i);
            } else {
                to = from + pokemonsPerProcessor -1;
            }
            pairs.add(Pair.of(from, to));
        }
        return pairs;
    }
    
    public static long getMillisecondsFromNanoseconds(long ns) {
        return TimeUnit.MILLISECONDS.convert(ns, TimeUnit.NANOSECONDS);
    }
    
    public static String getMillisecondsFromNanosecondsAsString(long ns) {
        return String.valueOf(NumberUtils.getMillisecondsFromNanoseconds(ns));
    }
}
