import React from "react";
import {
  createStyles,
  Grid,
  Theme,
  withStyles,
  WithStyles,
} from "@material-ui/core";
import { useLocation } from "react-router-dom";
import { getCapture } from "../../util/api";

function PaymentSuccess({ classes }: PaymentSuccessProps) {


  const s = useLocation().search;
  console.log(s);

  const token = s.substring(s.indexOf("=") + 1, s.lastIndexOf("&"));

  const past = async () => {
    return getCapture({ token });
  };

  window.onload = function() {
    past();
  };

  return (
    <Grid container component="main" className={classes.root}>
      <Grid item xs={12} sm={4} md={7} className={classes.steps}>
        PAYMENT SUCCESS
      </Grid>
    </Grid>
  );
}

const styles = (theme: Theme) =>
  createStyles({
    root: {
      height: "100vh",
      gridArea: "center",
      marginTop: "100px",
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
