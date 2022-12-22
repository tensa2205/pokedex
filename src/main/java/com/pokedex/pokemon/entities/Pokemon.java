package com.pokedex.pokemon.entities;

import com.pokedex.pokemon.dto.PokemonDTO;
import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Pokemon implements Serializable {
    
    private @Id @GeneratedValue(strategy = GenerationType.IDENTITY) Long id;
    private String name;
    private Long pokemonNumber;
    private String imageUrl;
    
    public static Pokemon fromDTO(PokemonDTO dto) {
        return Pokemon.builder()
                .name(dto.getName())
                .pokemonNumber(dto.getId())
                .imageUrl(dto.getImageUrl())
                .build();
    }
    
    public PokemonDTO toDTO() {
       return new PokemonDTO(this.getPokemonNumber(), this.getName(), this.getImageUrl());
    }
    
    @Override
    public boolean equals(Object o) {
      if (this == o) {
        return true;
      }
      if (!(o instanceof Pokemon)) {
        return false;
      }
      
      Pokemon pokemon = (Pokemon) o;
      return Objects.equals(this.id, pokemon.id) && Objects.equals(this.name, pokemon.name);
    }
    
    @Override
    public int hashCode() {
      return Objects.hash(this.id, this.name);
    }

    @Override
    public String toString() {
      return "Pokemon{" + "id=" + this.id + ", name='" + this.name + '\'';
    }
}
