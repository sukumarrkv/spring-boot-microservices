import { Component, DestroyRef, inject, input, OnInit, output, signal } from '@angular/core';
import { Product, ProductResponse } from './product.model';
import { ProductService } from './product.service';
import { HeaderComponent } from "../core/header/header.component";
import { PaginationComponent } from "./pagination/pagination.component";
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-product',
  standalone: true,
  imports: [PaginationComponent],
  templateUrl: './product.component.html',
  styleUrl: './product.component.css',
})
export class ProductComponent implements OnInit {
  products = signal<Product[]>([]);
  page = input<string>('0');
  productResponse = signal<ProductResponse>({
    data: [],
    totalElements: 0,
    pageNumber: 0,
    totalPages: 0,
    isFirst: true,
    isLast: false,
    hasNext: true,
    hasPrevious:  false
  });
  private productService = inject(ProductService);
  private activatedRoute = inject(ActivatedRoute);
  private destroRef = inject(DestroyRef);
  ngOnInit(): void {
    const queryParamsSubscription = this.activatedRoute.queryParams.subscribe(params => {
      this.loadProducts(params['page']);
    })

    this.destroRef.onDestroy(() => {
      queryParamsSubscription.unsubscribe();
    })
  }

  loadProducts(pageNumber: string) {
    this.productService.loadProducts(pageNumber).subscribe({
      next: (response) => {
        this.productResponse.set(response);
        this.products.set(response.data);
        console.log(this.productResponse());
      }
    })
  }

  addToCart(product: Product) {

  }
}
