import React from "react";
import Button from "@material-ui/core/Button";
// Types
// Styles
import { Wrapper } from "./CartItem.styles";
// eslint-disable-next-line import/no-cycle
import { BookItemType } from "../../pages/Books";

type Props = {
  item: BookItemType;
  addToCart: (clickedItem: BookItemType) => void;
  removeFromCart: (id: number) => void;
};

const CartItem: React.FC<Props> = ({ item, addToCart, removeFromCart }) => (
  <Wrapper>
    <div>
      <h3>{item.title}</h3>
      <div className="information">
        <p>Price: ${item.price}</p>
        <p>Total: ${(item.amount * item.price).toFixed(2)}</p>
      </div>
      <div className="buttons">
        <Button
          size="small"
          disableElevation
          variant="contained"
          onClick={() => removeFromCart(item.id)}
        >
          -
        </Button>
        <p>{item.amount}</p>
        <Button
          size="small"
          disableElevation
          variant="contained"
          onClick={() => addToCart(item)}
        >
          +
        </Button>
      </div>
    </div>
    <img src={item.cover} alt={item.title} />
  </Wrapper>
);

export default CartItem;
