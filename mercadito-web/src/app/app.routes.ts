import { Routes } from '@angular/router';
import { LoginComponent } from './login/login.component';
import { RegisterComponent } from './register/register.component';
import { CatalogComponent } from './catalog/catalog.component';
import { OrderHistoryComponent } from './order-history/order-history.component'; 

export const routes: Routes = [
    { path: '', component: LoginComponent },
    { path: 'register', component: RegisterComponent },
    { path: 'order-history', component: OrderHistoryComponent },
    { path: 'catalog', component: CatalogComponent }
  ];
