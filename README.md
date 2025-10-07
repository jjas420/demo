# Sección 1: Angular
1.	Crea un componente de Angular llamado TarjetaUsuario que reciba como @Input un objeto usuario con propiedades {nombre, email, edad} y lo muestre en un template.
    
•	Especifica la declaración del componente (@Component) y como definirías la propiedad @Input.
# componente TarjetaUsuarioComponent
import { Component, Input } from '@angular/core';

@Component({
  selector: 'app-tarjeta-usuario',
  standalone: true,
  imports: [],
  templateUrl: './tarjeta-usuario.component.html',
  styleUrl: './tarjeta-usuario.component.css'
})
export class TarjetaUsuarioComponent {

  // * Propiedad de entrada que recibe los datos del usuario desde el componente padre.
  // * Se espera un objeto con las propiedades: nombre, email y edad.

    @Input() usuario!: { nombre: string; email: string; edad: number };

}
## html de  componente TarjetaUsuarioComponent
<!--Muestra los datos del usuario recibidos por @Input-->

<div class="tarjeta">

    <p><strong>nombre:</strong> {{ usuario.email }}</p>
    <p><strong>Email:</strong> {{ usuario.email }}</p>
    <p><strong>Edad:</strong> {{ usuario.edad }} años</p>
</div>

# componente donde se le pasa los valores al imput 


import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { TarjetaUsuarioComponent } from "./Component/tarjeta-usuario/tarjeta-usuario.component";

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet, TarjetaUsuarioComponent],
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})
export class AppComponent {
  title = 'prueba';

  //Objeto de ejemplo que representa un usuario.
  // Contiene las propiedades necesarias para ser mostrado
  //en el componente TarjetaUsuario.
   
  usuarioEjemplo = {
    nombre: 'Jonathan Ayona',
    email: 'jonathan@gmail.com',
    edad: 25
  };

}

## html de  componente TarjetaUsuarioComponent
<main class="main">
  <router-outlet />
  <!-- Componente que muestra los datos del usuario en una tarjeta visual -->
  <app-tarjeta-usuario [usuario]="usuarioEjemplo"></app-tarjeta-usuario>  
</main>


sdas
