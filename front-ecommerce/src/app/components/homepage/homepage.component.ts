import {Component, OnInit} from '@angular/core';
import { CategoryService } from 'src/app/services/category.service';
import { ProductService } from 'src/app/services/product.service';
import { Category } from 'src/models/Category';
import { Product } from 'src/models/Product';

@Component({
  selector: 'app-homepage',
  templateUrl: './homepage.component.html',
  styleUrls: ['./homepage.component.css']
})
export class HomepageComponent implements OnInit{

  categories: Category[] = [];
  products: Product[] = [];

  constructor(private categoryService: CategoryService,
    private productService: ProductService){}

  ngOnInit(){
    this.findAllCategories();
    this.findAllProducts();
  }
  
  findAllCategories(){
      this.categoryService.findAllCategories().subscribe(
        categories => {this.categories = categories;
        });
    }

  findAllProducts(){
    this.productService.findAllProducts().subscribe(
      products => {this.products = products;
      });
  }

}
