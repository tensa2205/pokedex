package com.pokedex.pokemon.entities;

import com.pokedex.pokemon.dto.PokemonDTO;
import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
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
    
    @ManyToMany
    //JoinTable is used for the Owner side of the relationship. https://www.baeldung.com/jpa-many-to-many
    @JoinTable(
            name = "pokemon_has_type",
            joinColumns = @JoinColumn(name = "pokemon_id"),
            inverseJoinColumns = @JoinColumn(name = "type_id")
    )
    private Set<Type> types;
    
    public static Pokemon fromDTO(PokemonDTO dto, Set<Type> types) {
        return Pokemon.builder()
                .name(dto.getName())
                .pokemonNumber(dto.getId())
                .imageUrl(dto.getImageUrl())
                .types(types)
                .build();
    }
    
    public PokemonDTO toDTO() {
       return new PokemonDTO(this.getPokemonNumber(), this.getName(), this.getImageUrl(), this.getTypesAsString());
    }
    
    public Set<String> getTypesAsString() {
        Set<String> typesStr = new HashSet();
        this.getTypes().forEach(type -> typesStr.add(type.getName()));
        return typesStr;
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
