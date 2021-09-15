import React from "react";
import { Button, createStyles, Grid, Theme } from "@material-ui/core";
// eslint-disable-next-line import/no-cycle
import { BookItemType } from "../pages/Books";
import { Wrapper } from "./Book.styles";

type Props = {
  item: BookItemType;
  handleAddToCart: (clickedItem: BookItemType) => void;
};

const Book: React.FC<Props> = ({ item, handleAddToCart }) => (
  <Wrapper>
    <img src={item.cover} alt={item.title} />
    <div>
      <h3>{item.author}</h3>
      <p>{item.pageCount}</p>
      <h3>{item.isbn}</h3>
    </div>
    <Button onClick={() => handleAddToCart(item)}>Add to cart</Button>
  </Wrapper>
);

export default Book;
