import { HttpClient } from "@angular/common/http";
import { inject, Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { Product, ProductResponse } from "./product.model";

@Injectable({providedIn: 'root'})
export class ProductService {
  private http = inject(HttpClient);
  private apiUrl = 'http://localhost:8989/catalog/api/products';

  loadProducts(pageNumber: string) {
    if(pageNumber) {
      return this.http.get<ProductResponse>(this.apiUrl + '?page=' + pageNumber);
    }

    return this.http.get<ProductResponse>(this.apiUrl);
  }
}