import React from "react";
import {
  createStyles,
  Grid,
  Theme,
  withStyles,
  WithStyles,
} from "@material-ui/core";

function PaymentSuccess({ classes }: PaymentSuccessProps) {
  return (
    <Grid container component="main" className={classes.root}>
      <Grid item xs={12} sm={4} md={7} className={classes.steps} >
        PAYMENT SUCCESS
      </Grid>
    </Grid>
  );
}

const styles = (theme: Theme) =>
  createStyles({
    root: {
      height: "100vh",
    },
    steps: {
      "& img": {
        width: 230,
        margin: theme.spacing(6),
      },
    },
  });

type PaymentSuccessProps = WithStyles<typeof styles>;

export default withStyles(styles)(PaymentSuccess);
