import React, { useEffect } from "react";
import {
  Button,
  CircularProgress,
  createStyles,
  Grid,
  Theme,
  withStyles,
  WithStyles,
} from "@material-ui/core";
import { useLocation } from "react-router-dom";
import { useMutation } from "react-query";
import { Check, Clear } from "@material-ui/icons";
import { paymentCapture } from "../../util/api";
import Link from "../../util/Link";

function PaymentSuccess({ classes }: PaymentSuccessProps) {
  const loc = useLocation();
  const { mutate, isLoading, isError } = useMutation(paymentCapture);
  const token = new URLSearchParams(loc.search).get("token");
  useEffect(() => {
    if (token) mutate({ token });
  }, [mutate, token]);

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
        {isLoading ? (
          <CircularProgress />
        ) : (
          <>
            {isError || !token ? <Clear /> : <Check />}

            <h1>Payment {isError || !token ? "Fail!" : "Success!"}</h1>
            <Link to="/allBooks">
              <Button variant="contained" color="primary">
                Keep Browsing
              </Button>
            </Link>
          </>
        )}
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
