export interface Product {
   code: string,
	 name: string
	 description: string,
	 imageUrl: string,
	 price: string,
}

export interface ProductResponse {
  data: Product[],
	totalElements: number
	pageNumber: number
	totalPages: number
	isFirst: boolean
	isLast: boolean
	hasNext: boolean
	hasPrevious:  boolean
}

export interface CartRequest {
  items: Product[],
  totalAmount: number
	quantity: number
}