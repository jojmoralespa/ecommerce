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
        label: 'Registro de clientes',
        icon: 'pi pi-fw pi-file-edit',
        routerLink: "registro"
      },
      {
        label: 'Clientes',
        icon: 'pi pi-fw pi-users',
        routerLink: 'clientes'
      },
      {
        label: 'Agendamientos',
        icon: 'pi pi-fw pi-book',
        routerLink: 'agendamientos'
      }
    ];
  }

}
