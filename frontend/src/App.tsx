import React, { useEffect } from 'react';
import Select from 'react-select'
import './App.css';
import PokemonService from './client/PokemonService';
import Masterball from './Masterball';
import Pokemon from './model/Pokemon';
import { Type } from './model/Type';
import PaginateCards from './PaginateCards';

function capitalize(s : string) {
  return s[0].toUpperCase() + s.slice(1);
}

interface Option {
  value: string,
  label: string
}

const generateOptions = () : Array<Option> => {
  const types = Object.keys(Type);
  return types.map((type) => ( { value: type, label: capitalize(type.toLowerCase())} ))
}
function App() {
  const [loading, setLoading] = React.useState(true);
  const [pokemons, setPokemons] = React.useState<Array<Pokemon>>([]);
  const [findedPokemons, setFindedPokemons] = React.useState<Array<Pokemon>>([]);
  const [searchedName, setSearchedName] = React.useState("");
  const [selectedTypes, setSelectedTypes] = React.useState<Array<string>>([]);
  const options = generateOptions();
  
  useEffect(() => {
    document.body.style.overflow = "hidden";
    document.body.style.backgroundColor = "#FDF5BF";
  }, [])


  //Debounced useEffect
  useEffect(() => {
    if (loading) return;
    const getResult = setTimeout(() => {
      if (searchedName === '' && selectedTypes.length === 0) {
        setFindedPokemons(pokemons);
      } else {
        let tempList = pokemons;
        if (selectedTypes.length !== 0) {
          tempList = tempList.filter(pkm => anyTypeIncludes(pkm.types));
        }
        if (searchedName !== '') {
          tempList = tempList.filter(pkm => pkm.name.toLowerCase().includes(searchedName.toLowerCase()));
        }
        setFindedPokemons(tempList);
      }
    }, 750);
    return () => clearTimeout(getResult);
  }, [searchedName, selectedTypes, pokemons, loading])

  const anyTypeIncludes = (types : Array<Type>) : boolean => {
    const typesAsStr = types.map(type => type.toString());
    const found = selectedTypes.every(selectedType => typesAsStr.indexOf(selectedType) >= 0);
    return found;
  } 

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

  const onTypeSelect = (newValue: any) => {
    const selected : Array<Option> = newValue;
    if (selected.length) {
      setSelectedTypes(selected.map(option => option.value));
    } else {
      setSelectedTypes([])
    }
  }

  return (
    <>
      <div className="header" style={{display: "flex", flexDirection: "row", justifyContent: "space-between"}}>
        <div className="page-title">Pokedex</div>
      { !loading && 
          <>
          <div className='search-bar'>
            <label htmlFor="fname">Search by name: </label>
            <input type="text" id="pname" name="pname" onChange={onChange}/>
          </div>
          <Select className='search-select' options={options} onChange={onTypeSelect} isMulti/>
          </>
        }
      </div>
      { loading && <Masterball onOpen={onOpen}/> }

      { !loading &&
        <PaginateCards pokemons={findedPokemons}/>
      }
    </>
  );
}

export default App;
