import React from "react";
import {
  Button,
  CircularProgress,
  createStyles,
  Grid,
  Theme,
  withStyles,
  WithStyles,
} from "@material-ui/core";
import { Check, Clear } from "@material-ui/icons";
import Link from "../../util/Link";

function PaymentFailed({ classes }: PaymentFailedProps) {
  return (
    <Grid container component="main" className={classes.root}>
      <Grid
        container
        spacing={0}
        direction="column"
        alignItems="center"
        justify="center"
        style={{ minHeight: "100vh" }}
      >
        <Clear />
        <h1>Payment Processing Failed!</h1>
        <h4>Try again some other time?</h4>
        <Link to="/allBooks">
          <Button variant="contained" color="primary">
            Keep Browsing
          </Button>
        </Link>
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

type PaymentFailedProps = WithStyles<typeof styles>;

export default withStyles(styles)(PaymentFailed);
