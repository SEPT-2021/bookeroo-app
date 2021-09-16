import React, { useContext } from "react";
import {
  AppBar,
  Box,
  Button,
  createStyles,
  Theme,
  Toolbar,
  Typography,
  withStyles,
  WithStyles,
} from "@material-ui/core";
import { Person } from "@material-ui/icons";
import { useHistory } from "react-router-dom";
import logo from "../assets/logo.svg";
import Link from "../util/Link";
import { GlobalContext } from "./GlobalContext";

function NavBar({ classes }: NavBarProps) {
  const { user, signout } = useContext(GlobalContext);
  const history = useHistory();
  const onSignout = () => {
    signout();
    history.push("/");
  };
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
          {user ? (
            <Box display="flex" alignItems="center">
              <Person />
              <Typography variant="subtitle2">{user?.firstName}</Typography>
              <Button variant="contained" onClick={onSignout}>
                Sign out
              </Button>
            </Box>
          ) : (
            <>
              <Link to="/login">
                <Button variant="contained">Login</Button>
              </Link>
              <Link to="/register">
                <Button variant="contained" color="primary">
                  Register
                </Button>
              </Link>
            </>
          )}
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
