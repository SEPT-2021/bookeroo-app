export interface TokenProps {
  jwt: string;
}

export type ShippingItemType = {
  addressLine1?: string;
  addressLine2?: string;
  city?: string;
  state?: string;
  postalCode?: string;
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
  shippingAddress?: ShippingItemType;
};

export type RegisterAsSellerType = {
  abn: string;
  businessName: string;
  businessPhone: string;
};

export type ListingType = {
  id: string;
  price: string;
  bookCondition: string;
};

export type TransactionType = {
  id: {
    listingId: number;
    buyerId: number;
  };
  listing: {
    id: number;
    userFullName: string;
    price: string;
    bookCondition: string;
    createdAt: string;
    updatedAt: string;
    available: boolean;
  };
};
