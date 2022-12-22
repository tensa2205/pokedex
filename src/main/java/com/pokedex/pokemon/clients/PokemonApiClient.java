package com.pokedex.pokemon.clients;

// TODO - Implement RestTemplate

import com.pokedex.pokemon.dto.PokemonDTO;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class PokemonApiClient {
    private WebClient client;
    private static final Logger logger = LoggerFactory.getLogger(PokemonApiClient.class);
    private static final String API_BASE_URL = "https://pokeapi.co/api/v2/";
    private static final String POKEMON = "pokemon/";
    private static final int NUMBER_OF_POKEMONS = 50;
    
    PokemonApiClient() {
        this.client = WebClient.builder()
                        .exchangeStrategies(ExchangeStrategies.builder().codecs(clientCodecConfigurer -> clientCodecConfigurer.defaultCodecs().maxInMemorySize(1000000))
                        .build())
                        .baseUrl(API_BASE_URL)
                        .build();
    }
    
    public List<PokemonDTO> getAllPokemons() {
        List<PokemonDTO> result = new ArrayList<>();
        getPokemonIDs().forEach((id) -> result.add(PokemonDTO.fromJSON(getPokemonJSONFromAPI(id))));
        return result;
    }
    
    //ADD CACHING
    public List<PokemonDTO> getPokemonsInRangeId(Pair<Integer, Integer> idRange) {
        List<PokemonDTO> result = new ArrayList<>();
        getPokemonIDsInRange(idRange).forEach((id) -> {
            PokemonDTO tempDTO = PokemonDTO.fromJSON(getPokemonJSONFromAPI(id));
            logger.info(tempDTO.toString());
            result.add(tempDTO);
        });
        return result;
    }
    
    private String getPokemonJSONFromAPI(int id) {
        logger.info("[CLIENT] Retrieving pokemon with ID " + id);
        return client.get().uri(POKEMON + id).retrieve().bodyToMono(String.class).block();
    }
    
    private IntStream getPokemonIDs() {
        return IntStream.rangeClosed(1, NUMBER_OF_POKEMONS);
    }
    
    private IntStream getPokemonIDsInRange(Pair<Integer, Integer> idRange) {
        return IntStream.rangeClosed(idRange.getFirst(), idRange.getSecond());
    }
    
}
