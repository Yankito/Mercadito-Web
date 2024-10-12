import {Component, OnInit} from '@angular/core';
import {Product} from "../interfaces/product";
import { CommonModule } from '@angular/common';
import {Order} from "../interfaces/order";
import {Router} from "@angular/router";
import {HttpClient} from "@angular/common/http";
import Swal from 'sweetalert2'; // Importación de SweetAlert2

@Component({
  selector: 'app-shopping-cart',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './shopping-cart.component.html',
  styleUrl: './shopping-cart.component.css'
})
export class ShoppingCartComponent implements OnInit{
  shoppingCart: Product[] = [];
  isDropdownOpen: boolean = false; // Estado para mostrar/ocultar el dropdown
  order: Order = {} as Order;
  constructor(private http: HttpClient) {}


  ngOnInit() {
    this.shoppingCart = JSON.parse(localStorage.getItem('shoppingCart') || '[]');
  }


  purchase(accountId: number) {
    if (this.shoppingCart.length === 0) {
      Swal.fire('Carrito vacío', 'El carrito está vacío. Agrega productos antes de comprar.', 'warning'); // [[1]](https://sweetalert2.github.io/?utm_source=textcortex&utm_medium=zenochat)
      return;
    }
    this.order.accountId = accountId;
    this.order.productsIds = this.shoppingCart.map(product => product.id);
    this.order.timestamp = new Date().toISOString()
    this.http.post<any>('http://localhost:8083/purchase', {
      accountId: this.order.accountId,
      productsIds: this.order.productsIds,
      timestamp: this.order.timestamp
    }).subscribe(
      response => {
        localStorage.setItem('accountId', response.accountId);
        this.clearCart();
        Swal.fire('Compra realizada', '', 'success'); // [[1]](https://sweetalert2.github.io/?utm_source=textcortex&utm_medium=zenochat)
      },
      error => {
        Swal.fire('Compra fallida', 'No se pudo realizar la compra. Por favor, inténtalo nuevo más tarde.', 'error'); // [[1]](https://sweetalert2.github.io/?utm_source=textcortex&utm_medium=zenochat)
      }
    );
  }

  addToCart(product: Product) {
    this.shoppingCart.push(product);
    localStorage.setItem('shoppingCart', JSON.stringify(this.shoppingCart));
  }

  removeFromCart(product: Product) {
    const index = this.shoppingCart.indexOf(product);
    if (index > -1) {
      this.shoppingCart.splice(index, 1);
    }
  }

  clearCart() {
    this.shoppingCart = [];
    localStorage.removeItem('shoppingCart');
  }

  getCartItems() {
    return this.shoppingCart;
  }

  toggleDropdown() {
    this.isDropdownOpen = !this.isDropdownOpen;
  }

  totalCart(){
    let total = 0;
    for (let i = 0; i < this.shoppingCart.length; i++) {
      total += this.shoppingCart[i].price;
    }
    return total;
  }

  formatPrice(price: number): string {
    return `$${price.toLocaleString('es-CL', { minimumFractionDigits: 0, maximumFractionDigits: 0 })}`;
  }
}
