import { computed, Injectable, OnInit, signal } from "@angular/core";
import { Product } from "../product/product.model";
import { CartItem, CartRequest } from "./cart.model";

@Injectable({providedIn: 'root'})
export class CartService {
  cartQuantity = signal(0);
  
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
    console.log(JSON.parse(cart));
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

  updateItemQuantity(quantity: string, code: string) {
    const cart : CartRequest = this.getCart();
    const quantityToUpdate = parseInt(quantity);
    if(quantityToUpdate < 1) {
      cart.items = cart.items.filter(item => item.code !== code);
    } else {
      const cartItem = cart.items.find(item => item.code === code);

      if(cartItem) {
        cartItem.quantity = quantityToUpdate;
        //cartItem.price = cartItem.quantity * parseInt(cartItem.price);
      } else {
        alert("Product not found in the cart");
      }
    }

    localStorage.setItem('cart', JSON.stringify(cart));
    //this.updateCartQuantity();
  }
}