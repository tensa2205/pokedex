
package com.pokedex.pokemon.dto;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Getter
@Setter
@AllArgsConstructor
@JsonDeserialize(using = PokemonDTODeserializer.class)
public class PokemonDTO {
    private Long id;
    private String name;
    private String imageUrl;
    
    private static final Logger logger = LoggerFactory.getLogger(PokemonDTO.class);
    
    public static PokemonDTO fromJSON(String json) {
        PokemonDTO pkm = null;
        try {
            pkm = new ObjectMapper().readValue(json, PokemonDTO.class);
        } catch (JsonProcessingException e) {
            logger.info("Error occured");
        }
        return pkm;
    }
    
    public String toJson() {
        String jsonString = null;
        try { 
            jsonString = new ObjectMapper().writeValueAsString(this);
        } catch (JsonProcessingException e) {
            logger.error("[POKEMON_DTO] Error when converting object to json", e);
        }
        return jsonString;
    }

    @Override
    public String toString() {
        return "PokemonDTO{" + "id=" + id + ", name=" + name + ", imageUrl=" + imageUrl + '}';
    }

    
    
    
}
