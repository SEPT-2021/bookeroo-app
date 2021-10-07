import React from "react";
import { Button } from "@material-ui/core";
import AddIcon from "@material-ui/icons/Add";
import styled from "styled-components";
import { useParams } from "react-router-dom";
import { useQuery } from "react-query";
import { Box, LinearProgress } from "@mui/material";
import { findBookById } from "../../util/api";
import NotFoundPage from "../NotFoundPage";

export const Wrapper = styled.div`
  margin-top: 100px;
  margin-left: auto;
  margin-right: auto;
  width: 50%;
  border: 3px solid black;
  padding: 10px;
`;

function SingleBook() {
  const { id } = useParams<{ id: string }>();
  const {
    data: book,
    isLoading,
    error,
  } = useQuery("singleBook", () => findBookById({ id }));
  if (error) {
    return <NotFoundPage />;
  }
  if (isLoading || !book) {
    return (
      <Box marginTop="80px">
        <LinearProgress />
      </Box>
    );
  }
  return (
    <Wrapper>
      <img src={book.cover} alt={book.title} />
      <div>
        <h1>{book.title}</h1>
        <h2> by {book.author}</h2>
        <p>Page Count : {book.pageCount}</p>
        <p>Book isbn : {book.isbn}</p>
        <p>Book description : {book.description}</p>
      </div>
      <Button>
        <AddIcon />
        Add to cart
      </Button>
    </Wrapper>
  );
}

export default SingleBook;
