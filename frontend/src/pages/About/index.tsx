import React from "react";
import {
  createStyles,
  Grid,
  Theme,
  withStyles,
  WithStyles,
} from "@material-ui/core";
import logo from "../../assets/logo.svg";

function AboutUs({ classes }: AboutUsProps) {
  return (
    <Grid container component="main" className={classes.root}>
      <div className="row align-items-center my-5">
        <div className="col-lg-7">
          <img className="img-fluid rounded mb-4 mb-lg-0" src={logo} alt="" />
        </div>
        <div className="col-lg-5">
          <h1 className="font-weight-light">About Us</h1>
          <p>To be populated......</p>
        </div>
      </div>
    </Grid>
  );
}

const styles = (theme: Theme) =>
  createStyles({
    root: {
      display: "flex",
      alignItems: "center",
      justifyContent: "center",
      marginTop: "100px",
      height: "100vh",
    },
  });

type AboutUsProps = WithStyles<typeof styles>;

export default withStyles(styles)(AboutUs);
