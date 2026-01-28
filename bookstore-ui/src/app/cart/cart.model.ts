export interface CartRequest {
  items: CartItem[],
  totalAmount: number
}

export interface CartItem {
  code: string,
	name: string
	description: string,
	imageUrl: string,
	price: string,
  quantity: number
}