import React from "react";
import { createStyles, Theme } from "@material-ui/core/styles";
import {
  Button,
  Grid,
  TextField,
  withStyles,
  WithStyles,
} from "@material-ui/core";
import SearchBar from "../../components/searchBar";

function BookSearch({ classes }: BookSearchProps) {
  return (
    <Grid container component="main" className={classes.root}>
      <Grid item xs={12}>
        <SearchBar />
      </Grid>
      <Grid container spacing={2} justify="center">
        <div
          style={{
            display: "flex",
            justifyContent: "center",
            alignItems: "center",
            height: "100vh",
          }}
        >
          <form className={classes.box} noValidate autoComplete="off">
            <TextField
              id="outlined-secondary"
              label="Book ID"
              variant="outlined"
              color="secondary"
            />
            <Button variant="contained" color="primary">
              Find My Book!
            </Button>
          </form>
        </div>
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
      display: "flex",
      alignItems: "center",
      justifyContent: "center",
      flexDirection: "column",
    },
    paper: {
      margin: theme.spacing(8, 4),
      display: "flex",
      flexDirection: "column",
      alignItems: "center",
    },
    box: {
      "& > *": {
        margin: theme.spacing(1),
        width: "25ch",
      },
    },
    avatar: {
      margin: theme.spacing(1),
      backgroundColor: theme.palette.secondary.main,
    },
    submit: {
      margin: theme.spacing(3, 0, 2),
    },
    button: {
      margin: theme.spacing(1),
    },
  });

type BookSearchProps = WithStyles<typeof styles>;

export default withStyles(styles)(BookSearch);
