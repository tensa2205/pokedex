import React, { useEffect } from "react";
import "./Card.css";
import "./types.css"
import Pokemon from "./model/Pokemon";
import { Type } from "./model/Type";

interface Props {
    pokemon: Pokemon
}

export default function Card(props : Props) {
    const [pokemon, setPokemon] = React.useState<Pokemon>();
    useEffect(() => setPokemon(props.pokemon), [props.pokemon]);

    const getClassType = (type : String) => {
        return Type[type as keyof typeof Type];
    }
    return (
            <div className="card">
                
                <img className="img" src={pokemon?.imageUrl} alt="Avatar"></img>
                {/*IMPLEMENT PAGINATION BEFORE SHOWING IMAGES https://www.npmjs.com/package/react-paginate --> FRONTEND PAGINATION*/}
                <div className="container">
                    {pokemon?.name}
                    <div style={{display: "flex", flexDirection: "row", margin: "15px"}}>
                        <div className="types">
                        {pokemon?.types.map(type => (<div className={`type ${getClassType(type)}`}>{type}</div>))}
                        </div>
                    </div>
                </div>
            </div>        
    )
}

//https://betterprogramming.pub/make-a-flexbox-react-component-e96a038da7ec