import React from "react";
import { makeStyles } from "@material-ui/core/styles";
import { Grid } from "@material-ui/core";
import Image from "./Image";
import useWindowPosition from "../hook/useWindowPosition";
import { BookItemType } from "../util/types";

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
  onClick,
}: {
  books: BookItemType[];
  checked?: boolean;
  onClick: (clicked: BookItemType) => void;
}) {
  const classes = useStyles();
  const hookChecked = useWindowPosition("header");
  const checked = argChecked || hookChecked;

  return (
    <div className={classes.root} id="book-list">
      <Grid container id="header" alignItems="center" justifyContent="center">
        {books.map((book) => (
          <Grid item md={3} sm={6} xs={12} className={classes.gridItem}>
            <Image
              book={book}
              key={book.title}
              checked={checked}
              onCartClick={() => onClick(book)}
            />
          </Grid>
        ))}
      </Grid>
    </div>
  );
}
