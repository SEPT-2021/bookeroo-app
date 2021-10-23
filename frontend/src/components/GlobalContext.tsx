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
import { BookItemType, Listing, TokenProps, User } from "../util/types";
import useStickyState from "../util/useStickyState";

export interface CartItem {
  listing: Listing;
  book: BookItemType;
  phonenumber: string;
}

interface GlobalContextType {
  user?: User;

  login(data: TokenProps): void;

  signOut(): void;

  cartOpen: boolean;
  setCartOpen: Dispatch<SetStateAction<boolean>>;
  cartItems: CartItem[];

  addToCart(b: CartItem): void;

  removeFromCart(id: number): void;
}

export const GlobalContext = createContext<GlobalContextType>({} as never);

export const GlobalContextProvider: FC<unknown> = ({ children }) => {
  const [token, setToken] = useState<string>();
  const [cartOpen, setCartOpen] = useState(false);
  const [cartItems, setCartItems] = useStickyState<CartItem[]>([], "cart");
  const addToCart = (clickedItem: CartItem) => {
    setCartItems((prev) => {
      // 1. Is the item already added in the cart?
      const isItemInCart = prev.find(
        (item) => item.listing.id === clickedItem.listing.id
      );

      if (isItemInCart) {
        return cartItems;
      }
      // First time the item is added
      return [...prev, clickedItem];
    });
  };
  const removeFromCart = (id: number) => {
    setCartItems((prev) => prev.filter((l) => l.listing.id !== id));
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
