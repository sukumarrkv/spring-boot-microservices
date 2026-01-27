export interface CreateOrderRequest {
  items: ProductRequestToCreateOrder[],
  customer: Customer,
  deliveryAddress: Address
}

export interface ProductRequestToCreateOrder {
  code: string,
  name: string,
  price: string,
  quantity: number
}

export interface Customer {
  name: string,
  emailAddress: string,
  phoneNumber: string
}

export interface Address {
  addressLine1: string,
  addressLine2: string,
  city: string,
  state: string,
  zipCode: string,
  country: string
}