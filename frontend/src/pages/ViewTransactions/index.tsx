import React from "react";
import {
  Box,
  createStyles,
  Grid,
  withStyles,
  WithStyles,
} from "@material-ui/core";
import { useQuery } from "react-query";
import { viewTransactions } from "../../util/api";
import theme from "../../theme";

function ViewTransactions({ classes }: ViewTransactionsProps) {
  const { data, isLoading, error } = useQuery("orders", viewTransactions);

  console.log(data);

  return (
    <Grid container component="main" className={classes.root}>
      <Grid container spacing={2} justifyContent="center">
        <div
          style={{
            display: "flex",
            justifyContent: "center",
            alignItems: "center",
          }}
        >
          <form className={classes.box} noValidate autoComplete="off">
            <Box>
              <h1>data</h1>
            </Box>
          </form>
        </div>
      </Grid>
    </Grid>
  );
}

const styles = () =>
  createStyles({
    root: {
      height: "30vh",
    },
    box: {
      "& > *": {
        margin: theme.spacing(1),
        width: "25ch",
      },
    },
  });

type ViewTransactionsProps = WithStyles<typeof styles>;

export default withStyles(styles)(ViewTransactions);
