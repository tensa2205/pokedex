
package com.pokedex.pokemon.entities;

import java.io.Serializable;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
public class Type implements Serializable {
    private @Id @GeneratedValue(strategy = GenerationType.IDENTITY) Long id;
    @Column(unique=true)
    private String name;
    
    //Many To Many relationship set, also review Pokemon.java
    @ManyToMany(mappedBy = "types")
    Set<Pokemon> pokemons;
}
