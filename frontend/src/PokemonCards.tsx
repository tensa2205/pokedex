import React from "react";
import Card from "./Card";
import { motion } from "framer-motion"
import PokemonService from "./client/PokemonService";
import Pokemon from "./model/Pokemon";
import './PokemonCards.css';

export default function PokemonCards() {
    const [pokemons, setPokemons] = React.useState<Array<Pokemon>>([]);

    React.useEffect(() => {
        setTimeout(() => document.body.style.overflowY = "scroll", 1000); //Capaz ni falta hace activar el scroll si hago paginación.
        PokemonService.getAllPokemons().then((res) => {
            const pokemons = res.map(pkm => JSON.parse(pkm));
            setPokemons(pokemons.slice(0,25)); //Temporary
        })
    }, [])

    return (
        <motion.div className="cards-section" style={{display: "flex", flexFlow: "row wrap",flexDirection: "row", justifyContent: "space-between"}} animate={{ y: [850, 0], opacity: 1 }} transition={{ delay: .5}}>
            {pokemons.length !== 0 && pokemons.map((pokemon) => <Card key={pokemon.id} pokemon={pokemon}/>)}
        </motion.div>
    )
}