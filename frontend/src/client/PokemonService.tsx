import Fetcher from "./Fetcher";
class PokemonService extends Fetcher {
    
    constructor() {
        super("http://localhost:8080");
    }

    getAllPokemons(): Promise<string[]> {
        return this.get("/retrieve-pokemons").then((response) => response.json());
    }
}
export default new PokemonService();