import React from "react";
import { createStyles, withStyles, WithStyles } from "@material-ui/core";
import Header from "../../components/Header";
import backgroundImage from "../../assets/books/backgroundBook2.png";
import Footer from "../../components/Footer";

function HomePage({ classes }: HomePageProps) {
  return (
    <div className={classes.root}>
      <Header />
      <Footer />
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
