import { computed, Injectable, OnInit, signal } from "@angular/core";
import { Product } from "../product/product.model";
import { CartItem, CartRequest } from "./cart.model";

@Injectable({providedIn: 'root'})
export class CartService {
  cartQuantity = signal(0);

  readonly getCartQuantity = computed(() => this.cartQuantity);

  getCart() : CartRequest {
    let cart = localStorage.getItem('cart');
    let cartRequest : CartRequest = {
      items: [],
      totalAmount: 0
    };

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
      cartItem.quantity = cartItem.quantity + 1;
    } else {
      const newCartItem: CartItem = {
        code: product.code,
        name: product.name,
        description: product.description,
        imageUrl: product.imageUrl,
        price: product.price,
        quantity: 1
      }
      cart.items.push(newCartItem);
    }

    localStorage.setItem('cart', JSON.stringify(cart));
    this.updateCartQuantity();
  }

  updateCartQuantity() {
    this.cartQuantity.update(value => value + 1);
  }
}