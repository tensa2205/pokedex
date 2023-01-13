package com.pokedex.pokemon.dto;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import org.springframework.util.StringUtils;

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
        String name = StringUtils.capitalize(node.get("name").asText());
        String imageUrl = node.get("sprites").get("front_default").asText("");
        Iterator<JsonNode> types = node.get("types").elements();
        Set<String> typesStr = new HashSet<>();
        while (types.hasNext()) {
           typesStr.add(types.next().get("type").get("name").asText().toUpperCase());
        }
        
        return new PokemonDTO(id, name, imageUrl, typesStr);
    }
    
}
