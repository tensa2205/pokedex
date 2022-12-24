import React, { useEffect } from 'react';
import './App.css';
import Masterball from './Masterball';
import PokemonCards from './PokemonCards';

function App() {
  const [loading, setLoading] = React.useState(true);
  
  useEffect(() => {
    document.body.style.overflow = "hidden";
    document.body.style.backgroundColor = "#FDF5BF";
  }, [])

  const onOpen = () => {
    setLoading(false);
  }

  return (
    <>
      <header className="header"></header>
      { loading && <Masterball onOpen={onOpen}/> }

      { !loading && 
        <PokemonCards/>
      }
    </>
  );
}

export default App;
