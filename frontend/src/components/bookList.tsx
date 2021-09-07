import React from "react";
import { makeStyles } from "@material-ui/core/styles";
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
}));

export default function () {
  const classes = useStyles();
  const checked = useWindowPosition("header");

  // help pls Ryan
  // hey the images won't load...
  // they will load if we do something like
  // `url(${process.env.PUBLIC_URL + backgroundImage})`

  // this is just to display a few of our book in our home page :)

  return (
    <div className={classes.root} id="images">
      <Image
        imageUrl={books[0].imageUrl}
        title={books[0].title}
        description={books[0].description}
        checked={checked}
      />
      <Image
        imageUrl={books[1].imageUrl}
        title={books[1].title}
        description={books[1].description}
        checked={checked}
      />
      <Image
        imageUrl={books[2].imageUrl}
        title={books[2].title}
        description={books[2].description}
        checked={checked}
      />
    </div>
  );
}
