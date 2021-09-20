import React from "react";
import { Button } from "@material-ui/core";
import AddIcon from "@material-ui/icons/Add";
import styled from "styled-components";
import { BookItemType } from "../../util/types";

export const Wrapper = styled.div`
  margin-top: 80px;
`;

type Props = {
  item: BookItemType;
  handleAddToCart: (clickedItem: BookItemType) => void;
};

const Book: React.FC<Props> = ({ item, handleAddToCart }) => (
  <Wrapper>
    <img src={item.cover} alt={item.title} />
    <div>
      <h1>{item.title}</h1>
      <h2> by {item.author}</h2>
      <p>Page Count : {item.pageCount}</p>
      <p>Book isbn : {item.isbn}</p>
      <p>Book description : {item.description}</p>
    </div>
    <Button onClick={() => handleAddToCart(item)}>
      <AddIcon />
      Add to cart
    </Button>
  </Wrapper>
);

export default Book;
