import { Type } from "./Type";

export default interface Pokemon {
    id: number,
    name: string,
    imageUrl: string,
    types: Array<Type>
}