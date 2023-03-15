import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Product } from 'src/models/Product';

@Injectable({
  providedIn: 'root'
})
export class ProductService {

  baseURL:string = "http://localhost:8080/api";

  constructor(private http:HttpClient) { 
  }

  findAll(): Observable<Product[]>{
    return this.http.get<Product[]>(this.baseURL+"/product/list");
  }

}
