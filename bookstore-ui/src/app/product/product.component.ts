import { Component, inject, input, OnInit, output, signal } from '@angular/core';
import { Product, ProductResponse } from './product.model';
import { ProductService } from './product.service';
import { HeaderComponent } from "../core/header/header.component";
import { PaginationComponent } from "./pagination/pagination.component";

@Component({
  selector: 'app-product',
  standalone: true,
  imports: [PaginationComponent],
  templateUrl: './product.component.html',
  styleUrl: './product.component.css',
})
export class ProductComponent implements OnInit {
  products = signal<Product[]>([]);
  private productService = inject(ProductService);
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

  ngOnInit(): void {
    console.log('Page number', this.page())
    this.productService.loadProducts(this.page()).subscribe({
      next: (response) => {
        this.productResponse.set(response);
        this.products.set(response.data);
        console.log(this.productResponse());
      }
    })
  }
}
