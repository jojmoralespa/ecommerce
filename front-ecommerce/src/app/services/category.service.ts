import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Category } from 'src/models/Category';

@Injectable({
  providedIn: 'root'
})
export class CategoryService {

  baseURL:string = "http://localhost:8080/api";

  constructor(private http:HttpClient) { 
  }

  findAll(): Observable<Category[]>{
    return this.http.get<Category[]>(this.baseURL+"/category/list");
  }

  createCategory(category: Category): Observable<Category>{
    return this.http.post<Category>(this.baseURL+"/category/create",category);
  }
}
