import {Component, ElementRef, OnInit, Renderer2, ViewChild} from '@angular/core';
import {Router} from "@angular/router";
import {HttpClient} from "@angular/common/http";
import {Profile} from "../interfaces/profile";
import {Product} from "../interfaces/product";
import { CommonModule } from '@angular/common';
import { ShoppingCartComponent } from '../shopping-cart/shopping-cart.component';
import { OrderHistoryComponent } from '../order-history/order-history.component';
import {FormsModule} from "@angular/forms";
import Swal from "sweetalert2";

@Component({
  selector: 'app-catalog',
  standalone: true,
  imports: [FormsModule,CommonModule, ShoppingCartComponent,OrderHistoryComponent],
  templateUrl: './catalog.component.html',
  styleUrls: ['./catalog.component.css']
})

export class CatalogComponent implements OnInit{

  profile: Profile={} as Profile;
  products: Product[]=[];
  showProfileDropdown = false;
  @ViewChild(ShoppingCartComponent) shoppingCartComponent!: ShoppingCartComponent;

  constructor(private router: Router, private http: HttpClient) {}


  ngOnInit(): void {
    this.getProfile();
    this.getProducts();
  }

  // Get profile from server
  getProfile(){
      const accountId = localStorage.getItem('accountId');
    if (accountId) {
        this.http.get<Profile>('http://localhost:8081/profile?accountId='+accountId).subscribe(
            (response: Profile) => {
              this.profile = response;
            },
            error => {
              alert('Perfil no existe');
            }
        );
    } else {
        Swal.fire({
            title: "Inicie sesión antes de entrar al catálogo.",
            width: 600,
            padding: "3em",
            color: "#716add",
            backdrop: `
            rgba(0,0,123,0.4)
            url("https://media.tenor.com/-AyTtMgs2mMAAAAi/nyan-cat-nyan.gif")
            left top
            no-repeat`
        });
        this.router.navigate(['/']);
    }
  }

  getProducts(){
    this.http.get<Product[]>('http://localhost:8082/product').subscribe(
        (response: Product[]) => {
            this.products=response;
            localStorage.setItem("products", JSON.stringify(response));
        },
        error => {
          alert('Error al obtener productos');
        }
    );
  }
  openOrderHistory(){
    this.router.navigate(['/order-history']);
  }

  logOut(){
      localStorage.removeItem("accountId");
      this.router.navigate(['/']);
      localStorage.removeItem("shoppingCart");
  }

  addToCart(product: Product) {
    this.shoppingCartComponent.addToCart(product);
  }

    formatPrice(price: number): string {
        return `$${price.toLocaleString('es-CL', { minimumFractionDigits: 0, maximumFractionDigits: 0 })}`;
    }

  purchase() {
    this.shoppingCartComponent.purchase(this.profile.account.id);
  }
    protected readonly localStorage = localStorage;
}
