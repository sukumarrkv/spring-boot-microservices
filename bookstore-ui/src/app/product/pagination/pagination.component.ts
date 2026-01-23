import { Component, inject, Input, input, OnInit } from '@angular/core';
import { ProductResponse } from '../product.model';
import { ProductService } from '../product.service';
import { RouterLink } from "@angular/router";

@Component({
  selector: 'app-pagination',
  imports: [RouterLink],
  templateUrl: './pagination.component.html',
  styleUrl: './pagination.component.css',
})
export class PaginationComponent {
  // productResponse: ProductResponse = {
  //   data: [],
  //   totalElements: 0,
  //   pageNumber: 0,
  //   totalPages: 0,
  //   isFirst: true,
  //   isLast: false,
  //   hasNext: true,
  //   hasPrevious:  false

  // };
  private productService = inject(ProductService);
  //pageNumber: string = '0';
  page = input<string>('0')

  @Input() productResponse: ProductResponse = {
        data: [],
    totalElements: 0,
    pageNumber: 0,
    totalPages: 0,
    isFirst: true,
    isLast: false,
    hasNext: true,
    hasPrevious:  false
  }

  // ngOnInit(): void {
  //   console.log(this.page());
  //   this.productService.loadProducts(this.page()).subscribe({
  //     next: (response) => {
  //       this.productResponse = response;
  //       console.log("ProductResponse", this.productResponse);
  //     }
  //   })
  // }
}
