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

export type BookItemType = {
  id: number;
  title: string;
  author: string;
  pageCount: string;
  isbn: string;
  description: string;
  cover: string;
  price: number;
  amount: number;
};

export type DataItemType = {
  book: BookItemType;
  quantity: number;
};
export type CartType = {
  orderItems: DataItemType[];
  shippingAddress: ShippingItemType;
};

export type UserType = {
  id: number;
  username: string;
  password: string;
  firstName: string;
  lastName: string;
  role: string;
  enabled: boolean;
  created: Date;
  updated: Date;
};
