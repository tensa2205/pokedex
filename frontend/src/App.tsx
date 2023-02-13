import React, { useEffect } from 'react';
import Select, { StylesConfig } from 'react-select'
import './App.css';
import PokemonService from './client/PokemonService';
import Masterball from './Masterball';
import Pokemon from './model/Pokemon';
import { Type } from './model/Type';
import PaginateCards from './PaginateCards';

interface Option {
  value: string,
  label: string
}

function retrieveHexColorFromType(data : Option) : string {
  return getComputedStyle(document.documentElement).getPropertyValue(`--${data.value.toLowerCase()}`);
}

const colourStyles: StylesConfig<Option, true> = {
  control: (styles) => ({ ...styles, backgroundColor: 'white' }),
  multiValue: (styles, { data }) => {
    const color = retrieveHexColorFromType(data);
    return {
      ...styles,
      backgroundColor: color,
    };
  },
  multiValueLabel: (styles, { data }) => {
    const color = retrieveHexColorFromType(data);
    return {
      ...styles,
      color: "white",
      backgroundColor: color,
    };
  },
  multiValueRemove: (styles, { data }) => {
    const color = retrieveHexColorFromType(data);
    return {
      ...styles,
      color: color,
      ':hover': {
        color: "white",
        backgroundColor: color
      }
    };
  },
};

function capitalize(s : string) {
  return s[0].toUpperCase() + s.slice(1);
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
      <div className="header" style={{display: "flex", flexDirection: "row", justifyContent: "space-evenly"}}>
        <div className="page-title">Pokedex</div>
      { !loading && 
          <div style={{display: "flex", flexDirection: "row"}}>
            <div className='search-bar'>
              <input className='search-term' type="text" placeholder='Search by name' id="pname" name="pname" onChange={onChange}/>
            </div>
            <Select closeMenuOnSelect={false} className='search-select' options={options} onChange={onTypeSelect} styles={colourStyles} placeholder="Select type/s" isMulti/>
          </div>
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
