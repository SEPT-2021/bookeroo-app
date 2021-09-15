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
import Book from "../../Book/Book";
import { Wrapper } from "../../Book/Book.styles";

export type BookItemType = {
  id: number;
  title: string;
  author: string;
  pageCount: string;
  isbn: string;
  cover: string;
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

  const getTotalItems = (items: BookItemType[]) =>
    items.reduce((ack: number, item) => ack + item.amount, 0);
  const handleAddToCart = (clickedItem: BookItemType) => null;

  const handleRemoveFromCart = () => null;

  if (isLoading) return <LinearProgress />;
  if (error) return <div>something wrong</div>;

  return (
    <Wrapper>
      <Drawer anchor="right" open={cartOpen} onClose={() => setCartOpen(false)}>
        Cart goes here
      </Drawer>
      <StyledButton onClick={() => setCartOpen(true)}>
        <Badge badgeContent={getTotalItems(cartItems)} color="error">
          <AddShoppingCartIcon />
        </Badge>
      </StyledButton>
      <Grid container spacing={3}>
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
