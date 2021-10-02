import React from "react";
import { Button } from "@material-ui/core";
import AddIcon from "@material-ui/icons/Add";
import styled from "styled-components";

export const Wrapper = styled.div`
  margin-top: 100px;
  margin-left: auto;
  margin-right: auto;
  width: 50%;
  border: 3px solid black;
  padding: 10px;
`;

function SingleBook() {
  const retrievedBooks = localStorage.getItem("currentBook");
  const books = JSON.parse(retrievedBooks as string);

  return (
    <Wrapper>
      <img src={books.imageUrl} alt={books.title} />
      <div>
        <h1>{books.title}</h1>
        <h2> by {books.author}</h2>
        <p>Page Count : {books.pageCount}</p>
        <p>Book isbn : {books.isbn}</p>
        <p>Book description : {books.description}</p>
      </div>
      <Button>
        <AddIcon />
        Add to cart
      </Button>
    </Wrapper>
  );
}

export default SingleBook;
