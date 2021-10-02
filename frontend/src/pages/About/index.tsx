import React from "react";
import {
  createStyles,
  Grid,
  Theme,
  withStyles,
  WithStyles,
} from "@material-ui/core";

function AboutUs({ classes }: AboutUsProps) {
  return (
    <Grid container component="main" className={classes.root}>
      <h1>This is</h1>
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
