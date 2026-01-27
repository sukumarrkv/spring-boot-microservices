import { Injectable } from "@angular/core";
import { CartRequest, Product } from "./product.model";

@Injectable({providedIn: 'root'})
export class CartService {

  getCart() {
    let cart = localStorage.getItem('cart');

    let cartRequest : CartRequest[] = [{
      items: [],
      totalAmount: 0,
      quantity: 0
    }]

    if(!cart) {
      cart = JSON.stringify(cartRequest);
      localStorage.setItem('cart', cart);
    }

    return JSON.parse(cart);
  }

  addToCart(product: Product) {
    const cart : CartRequest = this.getCart();
    const cartItem = cart.items.find(item => item.code === product.code);

    if(cartItem) {
      cart.quantity = cart.quantity + 1;
    } else {
      cart.items.push()
    }
  }
}