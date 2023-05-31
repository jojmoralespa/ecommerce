import { Route } from '@angular/router';
import { SignInComponent } from './sign-in.component';

export const authSigninRoutes: Route[] = [
    {
        path     : '',
        component: SignInComponent
    }
];