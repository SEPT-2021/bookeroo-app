import React from "react";
import {
  withStyles,
  WithStyles,
  createStyles,
  Theme,
  Toolbar,
  Box,
  IconButton,
  AppBar,
  Button,
} from "@material-ui/core";
import logo from "../assets/logo.svg";
import NavBarDropDown from "./NavBarDropDown";
import Link from "../util/Link";

function NavBar({ classes }: NavBarProps) {
  return (
    <AppBar className={classes.appbar} elevation={5}>
      <Toolbar className={classes.appbarWrapper}>
        <Box display="flex" flexGrow={1} alignItems="center">
          <img src={logo} alt="logo" className={classes.navLogo} />
          <h1 className={classes.colorText}>Bookeroo.</h1>
          <Link to="/">
            <Button>Home</Button>
          </Link>
          <Link to="/add">
            <Button>Add Book</Button>
          </Link>
        </Box>
        <Box>
          <Link to="/login">
            <Button variant="contained">Login</Button>
          </Link>
          <Link to="/register">
            <Button variant="contained" color="primary">
              Register
            </Button>
          </Link>
        </Box>
      </Toolbar>
    </AppBar>
  );
}

const styles = (theme: Theme) =>
  createStyles({
    navLogo: {
      marginRight: theme.spacing(3),
      width: 60,
      height: 60,
    },
    appbar: {
      background: "white",
    },
    appbarWrapper: {
      width: "80%",
      margin: "0 auto",
      "& button": {
        margin: theme.spacing(0, 1),
      },
    },
    appbarTitle: {
      flexGrow: 1,
    },
    colorText: {
      color: "#5DA2D5",
      fontFamily: "Arial Rounded MT Bold",
    },
  });

type NavBarProps = WithStyles<typeof styles>;

export default withStyles(styles)(NavBar);
