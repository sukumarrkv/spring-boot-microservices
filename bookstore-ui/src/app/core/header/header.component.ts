import { Component, computed, inject } from '@angular/core';
import { CartService } from '../../cart/cart.service';
import { RouterLink } from "@angular/router";

@Component({
  selector: 'app-header',
  imports: [RouterLink],
  templateUrl: './header.component.html',
  styleUrl: './header.component.css',
})
export class HeaderComponent {
  private cartService = inject(CartService);
  cartQuantity = this.cartService.cartQuantity;
}
