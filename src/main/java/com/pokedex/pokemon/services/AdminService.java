package com.pokedex.pokemon.services;

import com.pokedex.pokemon.clients.PokemonApiClient;
import com.pokedex.pokemon.dto.PokemonDTO;
import com.pokedex.pokemon.entities.Pokemon;
import com.pokedex.pokemon.entities.PokemonRepository;
import com.pokedex.pokemon.entities.Type;
import com.pokedex.pokemon.entities.TypeRepository;
import com.pokedex.pokemon.tasks.PokemonsRetrieveTask;
import com.pokedex.pokemon.utils.NumberUtils;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
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
    
    private final PokemonRepository pokemonRepository;
    private final TypeRepository typeRepository;
    
    private static final Logger logger = LoggerFactory.getLogger(AdminService.class);
    ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
    
    public AdminService(PokemonRepository pkmRepository, TypeRepository typeRepository) {
        this.pokemonRepository = pkmRepository;
        this.typeRepository = typeRepository;
    }
    
    public void generatePokemons() {
        List<PokemonsRetrieveTask> tasks = generateTasks();
        logger.info("[ADMIN_SERVICE] Number of tasks: " + tasks.size());
        
        List<Future<List<PokemonDTO>>> taskFutures = executeTasks(tasks);
        List<PokemonDTO> toSavePokemonsDTO = getMergedResultsFromTasks(taskFutures);
        List<Pokemon> toSavePokemons = completePokemonsToInsertOnDatabase(toSavePokemonsDTO);
        
        logger.info("[ADMIN_SERVICE] Inserting " + toSavePokemonsDTO.size() + " pokemons");
        this.pokemonRepository.saveAll(toSavePokemons);
    }
    
    private List<PokemonDTO> getMergedResultsFromTasks(List<Future<List<PokemonDTO>>> taskFutures) {
        List<PokemonDTO> toSavePokemonsDTO = new ArrayList();
        
        taskFutures.forEach(res -> {
            try {
                toSavePokemonsDTO.addAll( res.get());
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        });
        return toSavePokemonsDTO;
    }
    
    private List<PokemonsRetrieveTask> generateTasks() {
        List<PokemonsRetrieveTask> tasks = new ArrayList(); //Refactor
        NumberUtils.getPokemonRanges().forEach(pair -> tasks.add(new PokemonsRetrieveTask(client, pair.getFirst(), pair.getSecond())));
        return tasks;
    }
    
    private List<Future<List<PokemonDTO>>> executeTasks(List<PokemonsRetrieveTask> tasks) {
        List<Future<List<PokemonDTO>>> taskFutures = new ArrayList();
        try {
            taskFutures = executorService.invokeAll(tasks);
        } catch (InterruptedException e) {
            System.out.println("InterruptedException");
        }
        executorService.shutdown();
        return taskFutures;
    }
    
    private List<Pokemon> completePokemonsToInsertOnDatabase(List<PokemonDTO> toSavePokemonsDTO) {
        List<Type> types = typeRepository.findAll();
        List<Pokemon> pkmnsToSave = new ArrayList<>();
        
        for(PokemonDTO pkmDTO : toSavePokemonsDTO) {
            Set<Type> specificTypesForPokemon = types.stream().filter(type -> pkmDTO.getTypes().contains(type.getName())).collect(Collectors.toSet());
            pkmnsToSave.add(Pokemon.fromDTO(pkmDTO, specificTypesForPokemon));
        }
        return pkmnsToSave;
    }
    
    public List<String> getPokemonsFromDB() {
        List<Pokemon> pokemons = this.pokemonRepository.findAll();
        List<PokemonDTO> pokemonsDTO = new ArrayList<>();
        pokemons.forEach(pkm -> pokemonsDTO.add(pkm.toDTO()));
        return pokemonsDTO.stream().map(pokemonDTO -> pokemonDTO.toJson()).collect(Collectors.toList());
    }
    
}
