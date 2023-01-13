package com.pokedex.pokemon.tasks;

import com.pokedex.pokemon.clients.PokemonApiClient;
import com.pokedex.pokemon.dto.PokemonDTO;
import com.pokedex.pokemon.utils.NumberUtils;
import java.util.List;
import java.util.concurrent.Callable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.util.Pair;

public class PokemonsRetrieveTask implements Callable<List<PokemonDTO>> {
    private final PokemonApiClient client;
    private final Pair<Integer, Integer> idRange;
    private static final Logger logger = LoggerFactory.getLogger(PokemonsRetrieveTask.class);
    
    public PokemonsRetrieveTask(PokemonApiClient client, int from, int to) {
        this.client = client;
        this.idRange = Pair.of(from, to);
    }
        

    @Override
    public List<PokemonDTO> call() throws Exception {
        long startTime = System.nanoTime();
        logger.info("[START][POKEMON_RETRIEVE]" + "[" + this.idRange + "]");
        List<PokemonDTO> pkmsDTO = client.getPokemonsInRangeId(this.idRange);
        logger.info("[FINISH][POKEMON_RETRIEVE]" + "[" + this.idRange + "]" + " Elapsed time: " + NumberUtils.getMillisecondsFromNanosecondsAsString(System.nanoTime() - startTime));
        return pkmsDTO;
    }
    
}
