import { useEffect, useState } from 'react';
import { useMediaQuery } from 'react-responsive'
import ReactPaginate from 'react-paginate';
import Pokemon from './model/Pokemon';
import PokemonCards from './PokemonCards';
import "./PaginateCards.css";


interface Props {
    pokemons: Array<Pokemon>;
}

const PaginateCards = (props: Props) => {
    const [pokemons, setPokemons] = useState<Array<Pokemon>>([]);
    const [currentPage, setCurrentPage] = useState(1);
    const [pokemonsPerPage, setPokemonsPerPage] = useState(10);

    useMediaQuery({ query: '(min-width: 1824px)' }, undefined, () => setPokemonsPerPage(10));
    useMediaQuery({ query: '(min-height: 910px)' }, undefined, () => setPokemonsPerPage(10));
    useMediaQuery({ query: '(max-width: 1224px)' }, undefined, () => setPokemonsPerPage(5));
    useMediaQuery({ query: '(max-height: 900px)' }, undefined, () => setPokemonsPerPage(5));
    

    useEffect(() => { 
        setPokemons(props.pokemons);
    }, [props.pokemons]);

    const indexOfLastPost = currentPage * pokemonsPerPage;
    const indexOfFirstPost = indexOfLastPost - pokemonsPerPage;
    const currentPokemons = pokemons.slice(indexOfFirstPost, indexOfLastPost);

    const paginate = ( { selected } : {selected: number} ) => {
        setCurrentPage(selected + 1);
    };

    return (
        <div>
            {pokemons && 
            <div>
                <PokemonCards pokemons={currentPokemons}/>
                <div> 
                    <ReactPaginate
                        onPageChange={paginate}
                        pageCount={Math.ceil(pokemons.length / pokemonsPerPage)}
                        previousLabel={'Prev'}
                        nextLabel={'Next'}
                        containerClassName={'pagination'}
                        pageLinkClassName={'page-number'}
                        previousLinkClassName={'page-number'}
                        nextLinkClassName={'page-number'}
                        activeLinkClassName={'active'}
                    />
                </div>
            </div>
            }
        </div>
    );
};

export default PaginateCards;