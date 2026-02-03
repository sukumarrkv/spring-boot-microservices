import { computed, Injectable, OnInit, signal } from "@angular/core";
import { Product } from "../product/product.model";
import { CartItem, CartRequest } from "./cart.model";

@Injectable({providedIn: 'root'})
export class CartService {
  cartItems = signal<CartItem[]>([]);
  cartQuantity = computed(() => this.cartItems.length);
  //subTotal = signal(0);
  
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
    //console.log(JSON.parse(cart));
    this.updateCartItems(cartRequest.items);
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

    this.updateCartItems(cart.items);
    localStorage.setItem('cart', JSON.stringify(cart));
    //this.updateCartQuantity();
  }

  // updateCartQuantity() {
  //   this.cartQuantity.update(value => value + 1);
  // }

  updateCartItems(cartItems: CartItem[]) {
    this.cartItems.set(cartItems);
    console.log(this.cartItems());
    console.log(this.cartQuantity());
    console.log(cartItems.length);
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
        //cartItem.price = cartItem.quantity * parseInt(cartItem.price);
      } else {
        alert("Product not found in the cart");
      }
    }

    localStorage.setItem('cart', JSON.stringify(cart));
    //this.updateCartQuantity();
  }

  // getSubTotal(item: CartItem) {
  //   const price = parseInt(item.price);
  //   const total = item.quantity * price;
  //   this.subTotal.set(total);
  // }
}