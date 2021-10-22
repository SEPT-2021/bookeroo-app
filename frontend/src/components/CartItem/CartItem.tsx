import React, { useContext } from "react";
import styled from "styled-components";
import { Button, IconButton } from "@material-ui/core";
import { Delete } from "@material-ui/icons";
import { BookItemType, Listing } from "../../util/types";
import { CartItem as CartItemType, GlobalContext } from "../GlobalContext";

const Wrapper = styled.div`
  display: flex;
  justify-content: space-between;
  font-family: Arial, Helvetica, sans-serif;
  border-bottom: 1px solid lightblue;
  padding-bottom: 20px;
  div {
    flex: 1;
  }
  .information,
  .buttons {
    display: flex;
    justify-content: space-between;
  }
  img {
    max-width: 80px;
    object-fit: cover;
    margin-left: 40px;
  }
`;

type Props = {
  item: CartItemType;
};

const CartItem: React.FC<Props> = ({ item: { listing, book } }) => {
  const { removeFromCart } = useContext(GlobalContext);
  return (
    <Wrapper>
      <div>
        <h3>{book.title}</h3>
        <div className="information">
          <p>Price: ${listing.price}</p>
        </div>
        <div className="buttons">
          <Button
            startIcon={<Delete />}
            onClick={() => removeFromCart(listing.id)}
            variant="contained"
            color="secondary"
          >
            Remove from Cart
          </Button>
        </div>
      </div>
      <img src={book.cover} alt={book.title} />
    </Wrapper>
  );
};

export default CartItem;
