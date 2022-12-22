package com.pokedex.pokemon.entities;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LoadDatabase {
    private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);
        
    //CommandLineRunner explanation: https://www.educba.com/spring-boot-commandlinerunner/
    
    /* Unused bean, fit good as explanation.
    @Bean
    CommandLineRunner initializeDatabase(PokemonRepository repository) {
        return args -> {
            log.info("Preloading " + repository.save(new Pokemon("Bulbasaur", 1)));
            log.info("Preloading " + repository.save(new Pokemon("Charmander", 4)));
            log.info("Preloading " + repository.save(new Pokemon("Squirtle", 7)));
        };
    }
    */
}
