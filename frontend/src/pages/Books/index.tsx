import React, { useState } from "react";
import { useQuery } from "react-query";
import {
  Badge,
  Drawer,
  Grid,
  IconButton,
  LinearProgress,
} from "@material-ui/core";
import styled from "styled-components";
import AddShoppingCartIcon from "@material-ui/icons/AddShoppingCart";
// eslint-disable-next-line import/no-cycle
import Book from "../../components/Book/Book";
import { Wrapper } from "../../components/Book/Book.styles";
// eslint-disable-next-line import/no-cycle
import Cart from "../../components/Cart/Cart";

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

type BookItemTypes = {
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
  book: BookItemTypes;
  quantity: number;
};

const StyledButton = styled(IconButton)`
  position: fixed;
  z-index: 100;
  right: 20px;
  top: 20px;
`;

const getBooks = async (): Promise<BookItemType[]> =>
  (await fetch(`/api/books/all`)).json();

const Books = () => {
  const [cartOpen, setCartOpen] = useState(false);
  const [cartItems, setCartItems] = useState([] as BookItemType[]);
  const { data, isLoading, error } = useQuery<BookItemType[]>(
    "books",
    getBooks
  );

  const getItems = () => {
    // eslint-disable-next-line @typescript-eslint/no-shadow
    return cartItems.map((obj) => ({
      book: obj,
      quantity: obj.amount,
    }));
  };


  const getTotalItems = (items: BookItemType[]) =>
    items.reduce((ack: number, item) => ack + item.amount, 0);

  const handleAddToCart = (clickedItem: BookItemType) => {
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

  const handleRemoveFromCart = (id: number) => {
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

  if (isLoading) return <LinearProgress />;
  if (error) return <div>something wrong</div>;

  return (
    <Wrapper>
      <Drawer anchor="right" open={cartOpen} onClose={() => setCartOpen(false)}>
        <Cart
          cartItems={cartItems}
          addToCart={handleAddToCart}
          removeFromCart={handleRemoveFromCart}
          sendToCart={getItems()}
        />
      </Drawer>
      <StyledButton onClick={() => setCartOpen(true)}>
        <Badge badgeContent={getTotalItems(cartItems)} color="error">
          <AddShoppingCartIcon />
        </Badge>
      </StyledButton>
      <Grid container spacing={4}>
        {data?.map((item) => (
          <Grid item key={item.id} xs={12} sm={4}>
            <Book item={item} handleAddToCart={handleAddToCart} />
          </Grid>
        ))}
      </Grid>
    </Wrapper>
  );
};

export default Books;
