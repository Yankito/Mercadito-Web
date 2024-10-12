import {Component, OnInit} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Order} from "../interfaces/order";
import {Product} from "../interfaces/product";
import {CommonModule} from '@angular/common';
import {Router} from "@angular/router";

@Component({
  selector: 'app-order-history',
  templateUrl: './order-history.component.html',
  standalone: true,
  imports: [CommonModule],
  styleUrls: ['./order-history.component.css']
})
export class OrderHistoryComponent implements OnInit{
  orders: Order[] = [];
  products: Product[] = [];

  constructor(private router: Router, private http: HttpClient) {}


  getOrders(){

    this.http.get<Order[]>('http://localhost:8083/purchase?accountId='+localStorage.getItem('accountId')).subscribe(
        (response: Order[]) => {
          this.orders = response;
          //alert('Lista de Compras: ' + JSON.stringify(response));
        },
        error => {
          alert('Error al obtener las compras');
        }
    );

  }

  getProductById(id: number): Product{
    return <Product>this.products.find(product => product.id == id);
  }


  openCatalogo(){
    this.router.navigate(['/catalog']);
  }

  ngOnInit(){
    this.getProducts();
    this.getOrders();
  }


  protected readonly localStorage = localStorage;

  private getProducts() {
    const storedProducts = localStorage.getItem("products");
    if (storedProducts) {
      this.products = JSON.parse(storedProducts);
    } else {
      this.getProducts();
    }
  }


  formatPrice(price: number): string {
    return `$${price.toLocaleString('es-CL', { minimumFractionDigits: 0, maximumFractionDigits: 0 })}`;
  }

}
