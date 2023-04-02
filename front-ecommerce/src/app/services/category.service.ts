import { Injectable } from '@angular/core';
import { HttpClient , HttpParams} from '@angular/common/http';
import { Observable } from 'rxjs';
import { Category } from 'src/models/Category';
import { Page } from 'src/models/page';

@Injectable({
  providedIn: 'root'
})
export class CategoryService {

  baseURL:string = "http://localhost:8080/api";

  constructor(private http:HttpClient) { 
  }

  findAllCategories(size: number, page: number , sort:string): Observable<Page<Category>>{
    let params = new HttpParams();
    // params = params.append('page', page.toString());
    // params = params.append('size', size.toString());
    // params = params.append('sort', sort);
    
    return this.http.get<Page<Category>>(`${this.baseURL+"/category/list"}`, {
      headers: {
        'Authorization': 'Bearer asdadsgads'
      },
      params: {
        page: page,
        sort: sort,
        size: size
      }
    });
  }

  createCategory(category: Category): Observable<Category>{
    return this.http.post<Category>(this.baseURL+"/category/create",category);
  }
}
