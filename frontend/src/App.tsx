import React, { useEffect } from 'react';
import './App.css';
import PokemonService from './client/PokemonService';
import Masterball from './Masterball';
import Pokemon from './model/Pokemon';
import PaginateCards from './PaginateCards';

function App() {
  const [loading, setLoading] = React.useState(true);
  const [pokemons, setPokemons] = React.useState<Array<Pokemon>>([]);
  
  useEffect(() => {
    document.body.style.overflow = "hidden";
    document.body.style.backgroundColor = "#FDF5BF";
  }, [])

  const onOpen = () => {
    setLoading(false);
    retrieveAllPokemons();
  }

  const retrieveAllPokemons = () => {
    PokemonService.getAllPokemons().then((res) => {
      setPokemons(res.map(pkm => JSON.parse(pkm)));
  })
  }

  return (
    <>
      <header className="header"></header>
      { loading && <Masterball onOpen={onOpen}/> }

      { !loading && 
        <PaginateCards pokemons={pokemons}/>
      }
    </>
  );
}

export default App;
