export interface TokenProps {
  jwt: string;
}

export type ShippingItemType = {
  addressLine1: string;
  addressLine2: string;
  city: string;
  state: string;
  postalCode: string;
};

type BookItem = {
  id: number;
  title: string;
  author: string;
  pageCount: string;
  isbn: string;
  description: string;
  cover: string;
  price: number;
};

export type DataItemType = {
  book: BookItem;
  quantity: number;
};
export type CartType = {
  orderItems: DataItemType[];
  shippingAddress: ShippingItemType;
};
