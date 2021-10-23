import React, { useContext } from "react";
import { useParams } from "react-router-dom";
import { useQuery } from "react-query";
import {
  Box,
  Button,
  Container,
  Grid,
  Paper,
  Typography,
} from "@material-ui/core";
import { ArrowBack, Edit } from "@material-ui/icons";
import { Rating } from "@mui/material";
import { findBookById } from "../../util/api";
import NotFoundPage from "../NotFoundPage";
import { GlobalContext } from "../../components/GlobalContext";
import DrawerCart from "../../components/DrawerCart";
import Link from "../../util/Link";
import { snakeCaseToNormalString } from "../../util/string-util";
import LinearLoading from "../../util/LinearLoading";
import ListingsTable from "./ListingsTable";
import { Role } from "../../util/types";
import Reviews from "./Reviews";
import AddListingDialog from "./AddListingDialog";

function SingleBook() {
  const { id } = useParams<{ id: string }>();
  const { user } = useContext(GlobalContext);
  const {
    data: book,
    isLoading,
    error,
  } = useQuery("singleBook", () => findBookById({ id }));
  if (error) {
    return <NotFoundPage />;
  }
  if (isLoading || !book) {
    return <LinearLoading />;
  }

  return (
    <>
      <DrawerCart />
      <Container style={{ marginTop: "100px" }}>
        <Link to="/allBooks">
          <Button color="primary" startIcon={<ArrowBack />}>
            Back to All Books
          </Button>
        </Link>
        <Grid container spacing={8}>
          <Grid item md={4} xs={12} sm={12}>
            <img
              src={book.cover}
              alt={book.title}
              style={{ borderRadius: 15, height: 500, width: "100%" }}
            />
          </Grid>
          <Grid item md={8} xs={12} sm={12}>
            <Paper style={{ width: "100%", height: "100%", padding: 3 }}>
              <Typography variant="h3">{book.title}</Typography>
              <Typography variant="h5" gutterBottom>
                by {book.author}
              </Typography>
              {book.rating ? (
                <Rating readOnly value={book.rating} precision={0.2} />
              ) : (
                "No reviews yet"
              )}
              <br />
              <Typography variant="caption">
                Page Count: {book.pageCount}
              </Typography>
              <p>Book ISBN: {book.isbn}</p>
              <p>Book Description: {book.description}</p>
              <p>Book Category: {snakeCaseToNormalString(book.bookCategory)}</p>
              {user?.role === Role.ROLE_ADMIN && (
                <Link to={`/editBook/${book.id}`}>
                  <Button
                    startIcon={<Edit />}
                    variant="contained"
                    color="primary"
                  >
                    Manage Book
                  </Button>
                </Link>
              )}
            </Paper>
          </Grid>
        </Grid>

        <Box mt={3}>
          <Box display="flex" alignItems="center" mb={2}>
            <Typography
              variant="h4"
              style={{ fontWeight: "bold", marginRight: 20 }}
            >
              Listings
            </Typography>
            {user && <AddListingDialog book={book} />}
          </Box>
          <ListingsTable book={book} listings={book.listings || []} />
        </Box>
        <Box mt={3}>
          <Typography gutterBottom variant="h4" style={{ fontWeight: "bold" }}>
            Reviews
          </Typography>
          <Reviews reviews={book.reviews || []} bookId={id} />
        </Box>
      </Container>
    </>
  );
}

export default SingleBook;
