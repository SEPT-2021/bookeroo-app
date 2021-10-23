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
  rating: number;
  isbn: string;
  description: string;
  cover: string;
  listings?: Array<Listing>;
  reviews?: Review[];
  bookCategory: BookCategory;
};

export interface Listing {
  id: number;
  userFullName: string | null;
  price: string;
  bookCondition: BookCondition;
  available: boolean;
}

export enum BookCondition {
  NEW = "NEW",
  FINE = "FINE",
  VERY_GOOD = "VERY_GOOD",
  FAIR = "FAIR",
  POOR = "POOR",
}

export enum BookCategory {
  SPECULATIVE_FICTION = "SPECULATIVE_FICTION",
}

export interface AddEditBookType {
  title: string;
  author: string;
  pageCount: string;
  isbn: string;
  category: string;
  description: string;
}

export type CartType = {
  orderItems: Listing[];
  shippingAddress?: ShippingItemType;
};

export enum Role {
  ROLE_USER = "ROLE_USER",
  ROLE_ADMIN = "ROLE_ADMIN",
  ROLE_SELLER = "ROLE_SELLER",
}

export interface User {
  id: number;
  username: string;
  password: string;
  firstName: string;
  lastName: string;
  addressLine1: string;
  addressLine2: string;
  city: string;
  state: string;
  postalCode: string;
  role: Role;
  enabled: boolean;
  createdAt: string;
  updatedAt: string | null;
}

export interface Review {
  id: number;
  text: string;
  userFullName: string;
  rating: number;
}
