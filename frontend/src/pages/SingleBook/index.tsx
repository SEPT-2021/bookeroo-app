import React, { useContext } from "react";
import { useParams } from "react-router-dom";
import { useQuery } from "react-query";
import { Box, Container, Grid, Paper, Typography } from "@mui/material";
import {
  Add,
  ArrowBack,
  Check,
  Clear,
  Edit,
  ShoppingCartOutlined,
} from "@material-ui/icons";
import { Button, IconButton, LinearProgress } from "@material-ui/core";
import { DataGrid, GridColDef, GridRowsProp } from "@mui/x-data-grid";
import { findBookById } from "../../util/api";
import NotFoundPage from "../NotFoundPage";
import { GlobalContext, Role } from "../../components/GlobalContext";
import DrawerCart from "../../components/DrawerCart";
import Link from "../../util/Link";
import { snakeCaseToNormalString } from "../../util/string-util";

function SingleBook() {
  const { id } = useParams<{ id: string }>();
  const { addToCart, user } = useContext(GlobalContext);
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
  const listingCols: GridColDef[] = [
    { field: "id", headerName: "ID", width: 100 },
    {
      field: "userFullName",
      headerName: "Seller Name",
      width: 200,
      valueFormatter: (val) => val.value || "No Name",
    },
    { field: "price", headerName: "Price", width: 150 },
    {
      field: "bookCondition",
      headerName: "Book Condition",
      width: 200,
      valueFormatter: ({ value }) =>
        typeof value === "string" ? snakeCaseToNormalString(value) : value,
    },
    {
      field: "available",
      headerName: "Available",
      width: 150,
      renderCell: ({ value }) => (value ? <Check /> : <Clear />),
    },
    {
      field: "",
      headerName: "Add to Cart",
      width: 150,
      sortable: false,
      renderCell: () => {
        return (
          <IconButton>
            <ShoppingCartOutlined />
          </IconButton>
        );
      },
    },
  ];
  const listingRows: GridRowsProp = book.listings || [];

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
          <Grid item md={4} xs={12} sm={12}>
            <img
              src={book.cover}
              alt={book.title}
              style={{ borderRadius: 15, height: 500, width: "100%" }}
            />
          </Grid>
          <Grid item md={8} xs={12} sm={12}>
            <Paper sx={{ width: "100%", height: "100%", padding: 3 }}>
              <Typography variant="h3">{book.title}</Typography>
              <Typography variant="h5" gutterBottom>
                by {book.author}
              </Typography>
              <Typography variant="caption">
                Page Count: {book.pageCount}
              </Typography>
              <p>Book ISBN: {book.isbn}</p>
              <p>Book Description: {book.description}</p>
              <p>Book Category: {snakeCaseToNormalString(book.bookCategory)}</p>
              <Button
                onClick={() => addToCart(book)}
                startIcon={<Add />}
                variant="contained"
                color="primary"
              >
                Add to cart
              </Button>
              {user?.role === Role.ROLE_ADMIN && (
                <Link to={`/editBook/${book.id}`}>
                  <IconButton color="secondary">
                    <Edit />
                  </IconButton>
                </Link>
              )}
            </Paper>
          </Grid>
        </Grid>

        <Box height={300} mt={3}>
          <Typography gutterBottom variant="h4" style={{ fontWeight: "bold" }}>
            Listings
          </Typography>
          <DataGrid columns={listingCols} rows={listingRows} />
        </Box>
      </Container>
    </>
  );
}

export default SingleBook;
