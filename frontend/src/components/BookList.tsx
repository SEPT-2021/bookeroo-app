import React from "react";
import { makeStyles } from "@material-ui/core/styles";
import { Grid } from "@material-ui/core";
import Image from "./Image";
import useWindowPosition from "../hook/useWindowPosition";
import books from "../static/books";

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

export default function BookList() {
  const classes = useStyles();
  const checked = useWindowPosition("header");

  return (
    <div className={classes.root} id="book-list">
      <Grid container id="header" alignItems="center" justifyContent="center">
        {books.map((book) => (
          <Grid item md={3} className={classes.gridItem}>
            <Image book={book} key={book.title} checked={checked} />
          </Grid>
        ))}
      </Grid>
    </div>
  );
}
