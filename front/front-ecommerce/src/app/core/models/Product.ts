import { Category } from "./Category";

export interface Product{
    id:number;
    name:string;
    price: number;
    description: string;
    imageUrl: string;
    category: Category;
}