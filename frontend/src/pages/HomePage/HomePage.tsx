import React from "react";
import {
  createStyles,
  CssBaseline,
  withStyles,
  WithStyles,
} from "@material-ui/core";
import Header from "../../components/Header/Header";
import backgroundImage from "../../assets/backgroundBook.png";

function HomePage({ classes }: HomePageProps) {
  return (
    <div className={classes.root}>
      <CssBaseline />
      <Header />
    </div>
  );
}

const styles = () =>
  createStyles({
    root: {
      minHeight: "100vh",
      backgroundImage: `url(${process.env.PUBLIC_URL + backgroundImage})`,
      backgroundRepeat: "no-repeat",
      backgroundSize: "cover",
    },
  });

type HomePageProps = WithStyles<typeof styles>;

export default withStyles(styles)(HomePage);
