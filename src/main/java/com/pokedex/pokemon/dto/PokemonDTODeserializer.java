package com.pokedex.pokemon.dto;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import java.io.IOException;

public class PokemonDTODeserializer extends StdDeserializer<PokemonDTO> {
    
    public PokemonDTODeserializer() { 
        this(null); 
    } 

    public PokemonDTODeserializer(Class<?> vc) { 
        super(vc); 
    }

    @Override
    public PokemonDTO deserialize(JsonParser jp, DeserializationContext dc) throws IOException, JacksonException {
        JsonNode node = jp.getCodec().readTree(jp);
        Long id = node.get("id").asLong();
        String name = node.get("name").asText();
        String imageUrl = node.get("sprites").get("front_default").asText("");
        return new PokemonDTO(id, name, imageUrl);
    }
    
}
