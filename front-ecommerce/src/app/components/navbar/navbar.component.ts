import {Component, OnInit} from '@angular/core';
import {MenuItem} from 'primeng/api';

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.css']
})
export class NavbarComponent implements OnInit {
  items!: MenuItem[];

  ngOnInit() {
    this.items = [
      {
        label: 'Explore',
        icon: 'pi pi-fw pi-compass',
        routerLink: "home"
      },
      {
        label: 'Shopping cart',
        icon: 'pi pi-fw pi-shopping-cart',
        routerLink: 'admin-categories'
      },
      {
        label: 'Account',
        icon: 'pi pi-fw pi-users',
        items: [
            {label: 'Admin', icon: 'pi pi-fw pi-file-edit', routerLink: 'admin'},
            {label: 'Log out', icon: 'pi pi-fw pi-sign-out'}
        ]
    }
    ];
  }

}
