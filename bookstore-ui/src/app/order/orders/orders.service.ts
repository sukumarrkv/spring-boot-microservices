import { HttpClient } from "@angular/common/http";
import { inject, Injectable } from "@angular/core";
import { CreateOrderRequest } from "./orders.model";

@Injectable({providedIn: 'root'})
export class OrdersService {
  private apiUrl = 'http://localhost:8989/catalog/api/order';
  private httpClient = inject(HttpClient);

  createOrder(createOrderRequest: CreateOrderRequest) {
    this.httpClient.post(this.apiUrl, createOrderRequest);
  }
}