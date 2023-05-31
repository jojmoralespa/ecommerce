
import { Routes } from '@angular/router';
import { AdminCategoryComponent } from './modules/admin/admin-category/admin-category.component';

export const appRoutes: Routes = [
  {
    path: '',
    children: [
      {
        path: 'sign-up',
        loadChildren: () => import('./modules/auth/sign-up/sign-up.module').then(m => m.SignUpModule)
      },
      {path: 'sign-in', loadChildren: () => import('app/modules/auth/sign-in/sign-in.module').then(m => m.SignInModule)},
    ]
  },
  {
    path: 'category',
    component: AdminCategoryComponent
  }

];

