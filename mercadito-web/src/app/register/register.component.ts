import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { HttpClient } from '@angular/common/http';
import { FormsModule } from '@angular/forms';
import Swal from 'sweetalert2'; // Importación de SweetAlert2

@Component({
  selector: 'app-register',
  standalone: true,
  imports: [FormsModule],
  templateUrl: './register.component.html'
})
export class RegisterComponent {
  email: string = '';
  password: string = '';
  name: string = '';
  phone: string = '';

  constructor(private router: Router, private http: HttpClient) {}

  onRegister() {
    if (this.email === '' || this.password === '' || this.name === '' || this.phone === '') {
      Swal.fire('Error de registro', 'Por favor, llena todos los campos.', 'error');
      return;
    } else if (this.password.length < 8) { 
      Swal.fire('Error de registro', 'La contraseña debe tener al menos 8 caracteres.', 'error');
      return; 
    }
    else if(this.email.indexOf('@') == -1){
      Swal.fire('Error de registro', 'El correo electrónico debe tener un formato válido.', 'error');
      return;
    }

    this.http.post<any>('http://localhost:8081/account/register', {
      email: this.email,
      password: this.password,
      name: this.name,
      phone: this.phone
    }).subscribe(
      response => {
        Swal.fire('Registro exitoso', 'Tu cuenta ha sido creada con éxito.','success');
        this.router.navigate(['/']);
      },
      error => {
        Swal.fire('Error de registro', 'No se pudo completar el registro. Por favor, inténtalo de nuevo más tarde.', 'error');
      }
    );
  }
}


