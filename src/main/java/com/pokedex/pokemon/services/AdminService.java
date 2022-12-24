package com.pokedex.pokemon.services;

import com.pokedex.pokemon.clients.PokemonApiClient;
import com.pokedex.pokemon.dto.PokemonDTO;
import com.pokedex.pokemon.entities.Pokemon;
import com.pokedex.pokemon.entities.PokemonRepository;
import com.pokedex.pokemon.tasks.PokemonsRetrieveTask;
import com.pokedex.pokemon.utils.NumberUtils;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminService {
    @Autowired PokemonApiClient client;
    
    private final PokemonRepository repository;
    
    private static final Logger logger = LoggerFactory.getLogger(AdminService.class);
    ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
    
    public AdminService(PokemonRepository repository) {
        this.repository = repository;
    }
    
    public void generatePokemons() {
        List<PokemonsRetrieveTask> tasks = new ArrayList();
        NumberUtils.getPokemonRanges().forEach(pair -> tasks.add(new PokemonsRetrieveTask(client, pair.getFirst(), pair.getSecond())));
        
        logger.info("[ADMIN_SERVICE] Number of tasks: " + tasks.size());
        List<Future<List<Pokemon>>> taskFutures = new ArrayList();
        try {
            taskFutures = executorService.invokeAll(tasks);
        } catch (InterruptedException e) {
            System.out.println("InterruptedException");
        }
        
        executorService.shutdown();

        
        List<Pokemon> toSavePokemons = new ArrayList();
        
        taskFutures.forEach(res -> {
            try {
                toSavePokemons.addAll( res.get());
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        });
        
        logger.info("[ADMIN_SERVICE] Inserting " + toSavePokemons.size() + " pokemons");
        this.repository.saveAll(toSavePokemons);
    }
    
    public List<String> getPokemonsFromDB() {
        List<Pokemon> pokemons = this.repository.findAll();
        List<PokemonDTO> pokemonsDTO = new ArrayList<>();
        pokemons.forEach(pkm -> pokemonsDTO.add(pkm.toDTO()));
        return pokemonsDTO.stream().map(pokemonDTO -> pokemonDTO.toJson()).collect(Collectors.toList());
    }
    
}
