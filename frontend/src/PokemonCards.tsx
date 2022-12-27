import React from "react";
import Card from "./Card";
import { motion } from "framer-motion"
import Pokemon from "./model/Pokemon";
import './PokemonCards.css';

interface Props {
    pokemons: Array<Pokemon>;
}

export default function PokemonCards(props: Props) {
    const [pokemons, setPokemons] = React.useState<Array<Pokemon>>([]);

    React.useEffect(() => {
        setPokemons(props.pokemons);
    }, [props.pokemons])

    return (
        <motion.div className="cards-section" style={{display: "flex", flexFlow: "row wrap",flexDirection: "row", justifyContent: "space-between"}} animate={{ y: [850, 0], opacity: 1 }} transition={{ delay: .5}}>
            {pokemons.length !== 0 && pokemons.map((pokemon) => <Card key={pokemon.id} pokemon={pokemon}/>)}
        </motion.div>
    )
}