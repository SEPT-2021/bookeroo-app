import React, { useContext } from "react";
import { Badge, Button, Drawer, Grid, IconButton } from "@material-ui/core";
import styled from "styled-components";
import { ShoppingCartOutlined } from "@material-ui/icons";
import { Box, BoxProps } from "@mui/material";
import CartItem from "../CartItem/CartItem";
import Link from "../../util/Link";
import { BookItemType } from "../../util/types";
import { GlobalContext } from "../GlobalContext";

export const Cart: React.FC<BoxProps & { hideCheckoutButton?: boolean }> = ({
  sx,
  hideCheckoutButton,
  ...rest
}) => {
  const { cartItems, addToCart, removeFromCart, setCartOpen } =
    useContext(GlobalContext);
  const calculateTotal = (items: BookItemType[]) =>
    items.reduce((ack: number, item) => ack + item.amount * item.price, 0);

  return (
    <Box
      sx={{
        fontFamily: "Arial, Helvetica, sans-serif",
        width: 500,
        padding: 2,
        ...sx,
      }}
      {...rest}
    >
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
      {!hideCheckoutButton && (
        <Grid item>
          <Link to="/checkout" variant="body2">
            <Button
              variant="contained"
              color="primary"
              onClick={() => setCartOpen(false)}
            >
              Checkout
            </Button>
          </Link>
        </Grid>
      )}
    </Box>
  );
};

const StyledButton = styled(IconButton)`
  position: fixed;
  right: 20px;
  top: 90px;
  background-color: orange;
`;

export default function DrawerCart() {
  const { cartItems, setCartOpen, cartOpen } = useContext(GlobalContext);

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
        <Cart />
      </Drawer>
    </>
  );
}
