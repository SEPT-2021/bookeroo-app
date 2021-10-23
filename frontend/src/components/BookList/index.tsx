import React from "react";
import { makeStyles } from "@material-ui/core/styles";
import { Grid } from "@material-ui/core";
import BookCard from "../Books";
import useWindowPosition from "../../hook/useWindowPosition";
import { BookItemType } from "../../util/types";

const useStyles = makeStyles((theme) => ({
  root: {
    minHeight: "100vh",
    display: "flex",
    justifyContent: "center",
    alignItems: "center",
    [theme.breakpoints.down("md")]: {
      flexDirection: "column",
    },
  },
  gridItem: {
    width: "100%",
  },
}));

export default function BookList({
  books,
  checked: argChecked = false,
}: {
  books: BookItemType[];
  checked?: boolean;
}) {
  const classes = useStyles();
  const hookChecked = useWindowPosition("header");
  const checked = argChecked || hookChecked;

  return (
    <div className={classes.root} id="book-list">
      <Grid container id="header" alignItems="center" justifyContent="center">
        {books.map((book) => (
          <Grid
            item
            md={3}
            sm={6}
            xs={12}
            className={classes.gridItem}
            key={book.id}
          >
            <BookCard book={book} key={book.title} checked={checked} />
          </Grid>
        ))}
      </Grid>
    </div>
  );
}
