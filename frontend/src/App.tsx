import React, { useEffect } from 'react';
import './App.css';
import PokemonService from './client/PokemonService';
import Masterball from './Masterball';
import Pokemon from './model/Pokemon';
import PaginateCards from './PaginateCards';

function App() {
  const [loading, setLoading] = React.useState(true);
  const [pokemons, setPokemons] = React.useState<Array<Pokemon>>([]);
  const [findedPokemons, setFindedPokemons] = React.useState<Array<Pokemon>>([]);
  const [searchedName, setSearchedName] = React.useState("");
  
  useEffect(() => {
    document.body.style.overflow = "hidden";
    document.body.style.backgroundColor = "#FDF5BF";
  }, [])

  //TODO - FIX SEARCH THAT IS NOT WORKING.
  //Debounced useEffect
  useEffect(() => {
    const getResult = setTimeout(() => {
      if (searchedName === '') {
        setPokemons(pokemons);
        setFindedPokemons([]);
      } else {
        setFindedPokemons(pokemons.filter(pkm => pkm.name.toLowerCase().includes(searchedName)));
      }
    }, 750);
    return () => clearTimeout(getResult);
  }, [pokemons, searchedName])

  const onOpen = () => {
    retrieveAllPokemons();
  }


  const retrieveAllPokemons = () => {
    PokemonService.getAllPokemons().then((res) => {
      setPokemons(res.map(pkm => JSON.parse(pkm)));
      setLoading(false);
    })
  }

  const onChange = (event : React.ChangeEvent<HTMLInputElement>) => {
    setSearchedName(event.target.value);
  }

  return (
    <>
      <header className="header">
        <div className="page-title">Pokedex</div>
      { !loading && 
          <div className='search-bar'>
            <label htmlFor="fname">Search by name: </label>
            <input type="text" id="pname" name="pname" onChange={onChange}/>
          </div>
        }
      </header>
      { loading && <Masterball onOpen={onOpen}/> }

      { !loading &&
        <PaginateCards pokemons={ findedPokemons.length ? findedPokemons : pokemons}/>
      }
    </>
  );
}

export default App;
