import { useEffect, useState } from 'react';
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
    const [pokemonsPerPage] = useState(10);


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