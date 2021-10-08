import React, {
  createContext,
  Dispatch,
  FC,
  SetStateAction,
  useEffect,
  useState,
} from "react";
import { useQuery } from "react-query";
import { api, profile } from "../util/api";
import { BookItemType, TokenProps } from "../util/types";
import useStickyState from "../util/useStickyState";

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
  roles: Role;
  enabled: boolean;
  createdAt: string;
  updatedAt: string | null;
}

interface GlobalContextType {
  user?: User;

  login(data: TokenProps): void;

  signOut(): void;

  cartOpen: boolean;
  setCartOpen: Dispatch<SetStateAction<boolean>>;
  cartItems: BookItemType[];

  addToCart(b: BookItemType): void;

  removeFromCart(id: number): void;
}

export const GlobalContext = createContext<GlobalContextType>({} as never);

export const GlobalContextProvider: FC<unknown> = ({ children }) => {
  const [token, setToken] = useState<string>();
  const [cartOpen, setCartOpen] = useState(false);
  const [cartItems, setCartItems] = useStickyState<BookItemType[]>([], "cart");
  const addToCart = (clickedItem: BookItemType) => {
    setCartItems((prev) => {
      // 1. Is the item already added in the cart?
      const isItemInCart = prev.find((item) => item.id === clickedItem.id);

      if (isItemInCart) {
        return prev.map((item) =>
          item.id === clickedItem.id
            ? { ...item, amount: item.amount + 1 }
            : item
        );
      }
      // First time the item is added
      return [...prev, { ...clickedItem, amount: 1 }];
    });
  };
  const removeFromCart = (id: number) => {
    setCartItems((prev) =>
      prev.reduce((ack, item) => {
        if (item.id === id) {
          if (item.amount === 1) return ack;
          return [...ack, { ...item, amount: item.amount - 1 }];
        }
        return [...ack, item];
      }, [] as BookItemType[])
    );
  };
  const {
    data: userData,
    refetch,
    remove,
    isError,
  } = useQuery("user", profile);
  // On token change, update the header and refetch user.
  useEffect(() => {
    if (token) {
      api.defaults.headers.Authorization = `Bearer ${token}`;
      refetch();
    } else {
      delete api.defaults.headers.Authorization;
      remove();
    }
  }, [refetch, remove, token]);
  // On startup, get token from local storage
  useEffect(() => {
    setToken(localStorage.getItem("token") || undefined);
  }, []);
  const signOut = () => {
    localStorage.removeItem("token");
    setToken(undefined);
  };
  // Clear token if error
  useEffect(() => {
    if (isError) {
      signOut();
    }
  }, [isError]);
  return (
    <GlobalContext.Provider
      value={{
        user: token ? userData : undefined,
        login: (data) => {
          localStorage.setItem("token", data.jwt as string);
          setToken(data.jwt);
        },
        signOut,
        cartOpen,
        setCartOpen,
        cartItems,
        addToCart,
        removeFromCart,
      }}
    >
      {children}
    </GlobalContext.Provider>
  );
};
