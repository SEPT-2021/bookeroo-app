import React from "react";
import {
  Button,
  createStyles,
  Grid,
  Link,
  Theme,
  withStyles,
  WithStyles,
} from "@material-ui/core";
import logo from "../../assets/logo.svg";

function NotFoundPage({ classes }: PageNotFound) {
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
        <img src={logo} alt="logo" />

        <h1>404 - Not Found!</h1>
        <Link href="/">
          <Button variant="contained" color="primary">
            Go Home
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
    },
    steps: {
      "& img": {
        width: 500,
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
    avatar: {
      margin: theme.spacing(1),
      backgroundColor: theme.palette.secondary.main,
    },
    submit: {
      margin: theme.spacing(3, 0, 2),
    },
  });

type PageNotFound = WithStyles<typeof styles>;

export default withStyles(styles)(NotFoundPage);
