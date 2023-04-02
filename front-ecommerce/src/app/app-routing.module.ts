import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import { AdminCategoryComponent } from './components/admin-category/admin-category.component';
import { AdminProductsComponent } from './components/admin-products/admin-products.component';
import { AdminComponent } from './components/admin/admin.component';
import { HomepageComponent } from './components/homepage/homepage.component';

const routes: Routes = [
  {path: '', component: HomepageComponent},
  {path: 'home', component: HomepageComponent},
  {path: 'admin', component: AdminComponent},
  {path: 'admin-categories', component: AdminCategoryComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
