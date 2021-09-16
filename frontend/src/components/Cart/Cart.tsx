import React from "react";
import { Button, Grid } from "@material-ui/core";
// eslint-disable-next-line import/no-cycle
import CartItem from "../CartItem/CartItem";
import { Wrapper } from "./Cart.styles";
// eslint-disable-next-line import/no-cycle
import Link from "../../util/Link";
import { BookItemType, DataItemType } from "../../util/types";

type Props = {
  cartItems: BookItemType[];
  addToCart: (clickedItem: BookItemType) => void;
  removeFromCart: (id: number) => void;
  sendToCart: DataItemType[];
};

const Cart: React.FC<Props> = ({
  cartItems,
  addToCart,
  removeFromCart,
  sendToCart,
}) => {
  const calculateTotal = (items: BookItemType[]) =>
    items.reduce((ack: number, item) => ack + item.amount * item.price, 0);
  /* items.reduce((ack: number, item) => ack + item.amount * 110, 0); */

  const setLocalStorageCart = () => {
    localStorage.setItem("cart", JSON.stringify(sendToCart));
  };

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
        <Link to="/checkout" variant="body2" onClick={setLocalStorageCart}>
          Checkout
        </Link>
      </Grid>
    </Wrapper>
  );
};

export default Cart;
