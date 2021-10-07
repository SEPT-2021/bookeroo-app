import React, { useContext } from "react";
import { Badge, Drawer, Grid, IconButton } from "@material-ui/core";
import styled from "styled-components";
import { ShoppingCartOutlined } from "@material-ui/icons";
import CartItem from "../CartItem/CartItem";
import Link from "../../util/Link";
import { BookItemType } from "../../util/types";
import { GlobalContext } from "../GlobalContext";

const Wrapper = styled.aside`
  font-family: Arial, Helvetica, sans-serif;
  width: 500px;
  padding: 20px;
`;

type Props = {
  cartItems: BookItemType[];
  addToCart: (clickedItem: BookItemType) => void;
  removeFromCart: (id: number) => void;
};

const Cart: React.FC<Props> = ({ cartItems, addToCart, removeFromCart }) => {
  const calculateTotal = (items: BookItemType[]) =>
    items.reduce((ack: number, item) => ack + item.amount * item.price, 0);

  return (
    <Wrapper>
      <h2>Your Shopping Cart</h2>
      {cartItems.length === 0 ? <p>No items in cart.</p> : null}
      {cartItems.map((item) => (
        <CartItem
          key={item.id}
          item={item}
          addToCart={addToCart}
          removeFromCart={removeFromCart}
        />
      ))}
      <h2>Total: ${calculateTotal(cartItems).toFixed(2)}</h2>
      <Grid item>
        <Link to="/checkout" variant="body2">
          Checkout
        </Link>
      </Grid>
    </Wrapper>
  );
};

const StyledButton = styled(IconButton)`
  position: fixed;
  right: 20px;
  top: 90px;
  background-color: orange;
`;

export default function DrawerCart() {
  const { addToCart, removeFromCart, cartItems, setCartOpen, cartOpen } =
    useContext(GlobalContext);

  const getTotalItems = (items: BookItemType[]) =>
    items.reduce((ack: number, item) => ack + item.amount, 0);
  return (
    <>
      <StyledButton onClick={() => setCartOpen(true)}>
        <Badge badgeContent={getTotalItems(cartItems)} color="error">
          <ShoppingCartOutlined />
        </Badge>
      </StyledButton>
      <Drawer anchor="right" open={cartOpen} onClose={() => setCartOpen(false)}>
        <Cart
          cartItems={cartItems}
          addToCart={addToCart}
          removeFromCart={removeFromCart}
        />
      </Drawer>
    </>
  );
}
