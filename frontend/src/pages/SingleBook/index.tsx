import React, { useContext } from "react";
import { useParams } from "react-router-dom";
import { useQuery } from "react-query";
import { Box, Card, Container, Grid, Typography } from "@mui/material";
import { Add, ArrowBack } from "@material-ui/icons";
import { Button, LinearProgress } from "@material-ui/core";
import { findBookById } from "../../util/api";
import NotFoundPage from "../NotFoundPage";
import { GlobalContext } from "../../components/GlobalContext";
import DrawerCart from "../../components/DrawerCart";
import Link from "../../util/Link";

function SingleBook() {
  const { id } = useParams<{ id: string }>();
  const { addToCart } = useContext(GlobalContext);
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
    <>
      <DrawerCart />
      <Container sx={{ marginTop: "100px" }}>
        <Link to="/allBooks">
          <Button color="primary" startIcon={<ArrowBack />}>
            Back to All Books
          </Button>
        </Link>
        <Grid container spacing={8}>
          <Grid item md={4}>
            <img
              src={book.cover}
              alt={book.title}
              style={{ borderRadius: 15, height: 500, width: "100%" }}
            />
          </Grid>
          <Grid item md={8}>
            <Card sx={{ width: "100%", height: "100%", padding: 3 }}>
              <Typography variant="h3">{book.title}</Typography>
              <Typography variant="h5" gutterBottom>
                {" "}
                by {book.author}
              </Typography>
              <Typography variant="caption">
                Page Count : {book.pageCount}
              </Typography>
              <p>Book isbn : {book.isbn}</p>
              <p>Book description : {book.description}</p>
              <Button
                onClick={() => addToCart(book)}
                startIcon={<Add />}
                variant="contained"
                color="primary"
              >
                Add to cart
              </Button>
            </Card>
          </Grid>
        </Grid>
      </Container>
    </>
  );
}

export default SingleBook;
