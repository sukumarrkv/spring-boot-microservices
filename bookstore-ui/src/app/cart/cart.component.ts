import { Component, inject, OnInit, signal } from '@angular/core';
import { FormControl, FormGroup, FormsModule, ReactiveFormsModule } from '@angular/forms';
import { CartService } from './cart.service';
import { CartItem, CartRequest } from './cart.model';
import { RouterLink } from "@angular/router";
import { CommonModule } from '@angular/common';
import { Product, ProductResponse } from '../product/product.model';

@Component({
  selector: 'app-cart',
  imports: [RouterLink, FormsModule, CommonModule, ReactiveFormsModule],
  templateUrl: './cart.component.html',
  styleUrl: './cart.component.css',
})
export class CartComponent implements OnInit{
  cartService = inject(CartService);
  //subTotal = signal(0);
  cartItems : CartItem[] = this.cartService.cartItems();
  // cart: CartRequest = {
  //   items: [],
  //   totalAmount: 0
  // }

  cartForm = new FormGroup({
    customer: new FormGroup({
      name: new FormControl('test'),
      email: new FormControl('test@test.com'),
      phoneNumber: new FormControl('7578676')
    }),
    address: new FormGroup({
      addressLine1: new FormControl('test1'),
      addressLine2: new FormControl('test2'),
      city: new FormControl('Test city'),
      state: new FormControl('Test state'),
      zipCode: new FormControl('24234'),
      country: new FormControl('India')
    })
  })

  ngOnInit(): void {
    //this.cart = this.cartService.getCart();
    //this.cartItems = this.cartService.cartItems();
  }

  createOrder() {
    console.log('Order Created Successfully');
  }

  updateItemQuantity(quantity: string, item: CartItem) {
    this.cartService.updateItemQuantity(quantity, item.code);
    //this.cartService.getSubTotal(item);
    //this.getSubTotal(item);
  }

  getSubTotal(item: CartItem) {
    const price = parseInt(item.price);
    const total = item.quantity * price;
    //return total;
    item.price = String(total);
  }
}
