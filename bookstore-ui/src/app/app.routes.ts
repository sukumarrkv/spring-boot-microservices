import { Routes } from '@angular/router';
import { ProductComponent } from './product/product.component';
import { CartComponent } from './cart/cart.component';

export const routes: Routes = [
  {
    path:'',
    component: ProductComponent
  },

  {
    path: 'products',
    component: ProductComponent
  },
  {
    path: 'cart',
    component: CartComponent
  }
];
