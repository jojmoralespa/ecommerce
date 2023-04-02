import { Component, OnInit } from '@angular/core';
import { FormBuilder } from '@angular/forms';
import { MenuItem} from 'primeng/api';
import { CategoryService } from 'src/app/services/category.service';
import { Category } from 'src/models/Category';

@Component({
  selector: 'app-admin-category',
  templateUrl: './admin-category.component.html',
  styleUrls: ['./admin-category.component.css']
})
export class AdminCategoryComponent implements OnInit{

  categories: Category[] = [];
  cols!: any[];
  items!: MenuItem[];
  category!: Category;

  displaySaveDialog: boolean = false;

  size: number = 10
  page: number = 0
  sort: string = "id"

  constructor(private categoryService: CategoryService,
    private formBuilder: FormBuilder){}

  ngOnInit(){
    this.findAllCategories();

    this.cols =[
      {field: "id" , header: "ID"},
      {field: "imageUrl" , header: "Image"},
      {field: "name" , header: "Name"},
      {field: "description" , header: "Description"} 
    ];
    
    this.items = [
    {
      label: "Nuevo",
      icon: 'pi pi-fw pi-plus',
      command : ()=> this.showMessageDialog()
    }
  ];
  }

  showMessageDialog(){
    this.displaySaveDialog = true;
  }
  
  findAllCategories(){
      this.categoryService.findAllCategories(this.size, this.page, this.sort).subscribe(
        page => {this.categories = page.content;
        });
    }

}
