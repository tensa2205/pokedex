import React, { useEffect } from "react";
import "./Card.css";
import Pokemon from "./model/Pokemon";

interface Props {
    pokemon: Pokemon
}

export default function Card(props : Props) {
    const [pokemon, setPokemon] = React.useState<Pokemon>();
    useEffect(() => setPokemon(props.pokemon), [props.pokemon]);
    return (
            <div className="card">
                
                <img className="img" src={pokemon?.imageUrl} alt="Avatar"></img>
                {/*IMPLEMENT PAGINATION BEFORE SHOWING IMAGES https://www.npmjs.com/package/react-paginate --> FRONTEND PAGINATION*/}
                <div className="container">
                    {pokemon?.name}
                    <div style={{display: "flex", flexDirection: "row", margin: "15px"}}>
                    <img className="img type" src="https://upload.wikimedia.org/wikipedia/commons/9/9d/Normal_Pokemon.svg" alt="Type"></img>
                    <img className="img type" src="https://upload.wikimedia.org/wikipedia/commons/9/9d/Normal_Pokemon.svg" alt="Type"></img>
                    </div>
                </div>
            </div>        
    )
}

//https://betterprogramming.pub/make-a-flexbox-react-component-e96a038da7ec