import React from "react";

import {
  createStyles,
  Grid,
  Theme,
  withStyles,
  WithStyles,
} from "@material-ui/core";

function LoginSuccess({ classes }: LoginSuccessProps) {
  return (
    <Grid className={classes.root}>
      <h1> LOGIN SUCCESS </h1>
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
    avatar: {
      margin: theme.spacing(1),
      backgroundColor: theme.palette.secondary.main,
    },
    submit: {
      margin: theme.spacing(3, 0, 2),
    },
  });

type LoginSuccessProps = WithStyles<typeof styles>;

export default withStyles(styles)(LoginSuccess);
