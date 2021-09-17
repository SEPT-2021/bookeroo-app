import React, { useState } from "react";
import { useQuery } from "react-query";
import {
  Badge,
  Box,
  Container,
  Drawer,
  IconButton,
  LinearProgress,
} from "@material-ui/core";
import styled from "styled-components";
// eslint-disable-next-line import/no-cycle
import { ShoppingCartOutlined } from "@material-ui/icons";
import { Wrapper } from "../../components/Book/Book.styles";
// eslint-disable-next-line import/no-cycle
import Cart from "../../components/Cart/Cart";
// eslint-disable-next-line import/no-cycle
import { getAllBooks, getBookBySearchTerm } from "../../util/api";
import { BookItemType } from "../../util/types";
import BookList from "../../components/BookList";
import SearchBar from "../../components/searchBar";

const StyledButton = styled(IconButton)`
  margin-right: 30px;
  border-radius: 50%;
  margin-top: 10px;
  background-color: orange;
`;

const Books = () => {
  const [cartOpen, setCartOpen] = useState(false);
  const [cartItems, setCartItems] = useState([] as BookItemType[]);
  const [search, setSearch] = useState("");
  const { data, isLoading, error, remove } = useQuery("books", () =>
    getBookBySearchTerm({ searchTerm: search })
  );
  const onSearch = () => remove();
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
      <Container>
        <Box display="flex" justifyContent="space-evenly">
          <SearchBar
            searchTerm={search}
            setSearchTerm={setSearch}
            search={onSearch}
          />
          <StyledButton onClick={() => setCartOpen(true)}>
            <Badge badgeContent={getTotalItems(cartItems)} color="error">
              <ShoppingCartOutlined />
            </Badge>
          </StyledButton>
        </Box>
        <BookList books={data || []} onClick={handleAddToCart} checked />
      </Container>
    </Wrapper>
  );
};

export default Books;
