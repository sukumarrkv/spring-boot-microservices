import { computed, Injectable, OnInit, signal } from "@angular/core";
import { Product } from "../product/product.model";
import { CartItem, CartRequest } from "./cart.model";

@Injectable({providedIn: 'root'})
export class CartService {
  cartItems = signal<CartItem[]>([]);
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

    return JSON.parse(cart);
  }

  addToCart(product: Product) {
    const cart : CartRequest = this.getCart();
    const cartItem = cart.items.find(item => item.code === product.code);

    if(cartItem) {
      cartItem.quantity = cartItem.quantity + 1;
      this.updateCartItems(cartItem);
    } else {
      const newCartItem: CartItem = {
        code: product.code,
        name: product.name,
        description: product.description,
        imageUrl: product.imageUrl,
        price: product.price,
        quantity: 1
      }
      this.updateCartItems(newCartItem);
      cart.items.push(newCartItem);
    }

    //console.log("Cart items in add cart method: ", cart.items);
    localStorage.setItem('cart', JSON.stringify(cart));
  }

  updateCartItems(cartItem: CartItem) {
    this.cartItems.update(items => items.filter(item => item.code !== cartItem.code))
    //this.cartItems.set(this.cartItems().filter(item => item.code !== cartItem.code));
    //this.cartItems.update()
    this.cartItems.update(items => [...items, cartItem]);
    //this.cartItems.set([...this.cartItems(), cartItem]);
    this.cartQuantity.set(this.cartItems().length);
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
        const price = parseInt(cartItem.price);
        const total = cartItem.quantity * price;
        cartItem.price = String(total);
        this.updateCartItems(cartItem);
      } else {
        alert("Product not found in the cart");
      }
    }
    localStorage.setItem('cart', JSON.stringify(cart));
  }
}