import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { HttpClient } from '@angular/common/http';
import { FormsModule } from '@angular/forms';
import Swal from 'sweetalert2'; // Importación de SweetAlert2

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [FormsModule],
  templateUrl: './login.component.html'
})
export class LoginComponent implements OnInit{
  email: string = '';
  password: string = '';


  constructor(private router: Router, private http: HttpClient) {}

  ngOnInit(): void {
    const accountId = localStorage.getItem('accountId');
    if (accountId) {
      this.router.navigate(['/catalog']);
    }
  }

  onLogin() {
    this.http.post<any>('http://localhost:8081/account/login', { email: this.email, password: this.password }).subscribe(
      response => {
        localStorage.setItem('accountId', response.accountId);

        this.router.navigate(['/catalog']);
      },
      error => {
        Swal.fire('Error de inicio de sesión', 'El correo electrónico o la contraseña son incorrectos.', 'error'); // [[1]](https://stackoverflow.com/questions/38677664/how-to-use-sweetalert2-in-angular2?utm_source=textcortex&utm_medium=zenochat)
      }
    );
  }

  onRegister() {
    this.router.navigate(['/register']);
  }
}
