import React, { useEffect, useState } from "react";
import {
  AppBar,
  Box,
  Collapse,
  createStyles,
  IconButton,
  Theme,
  Toolbar,
  withStyles,
  WithStyles,
} from "@material-ui/core";
import ExpandMoreIcon from "@material-ui/icons/ExpandMore";
import { Link as Scroll } from "react-scroll";
import logo from "../assets/logo.svg";
import NavBarDropDown from "./NavBarDropDown";

function Header({ classes }: HeaderProps) {
  const [checked, setChecked] = useState(false);
  useEffect(() => {
    setChecked(true);
  }, []);
  return (
    <div className={classes.root} id="header">
      <AppBar className={classes.appbar} elevation={5}>
        <Toolbar className={classes.appbarWrapper}>
          <Box display="flex" flexGrow={1} alignItems="center">
            <img src={logo} alt="logo" className={classes.navLogo} />
            <h1 className={classes.colorText}>Bookeroo.</h1>
          </Box>
          <Box className={classes.buttons}>
            <IconButton>
              <NavBarDropDown />
            </IconButton>
          </Box>
        </Toolbar>
      </AppBar>
      <Collapse
        in={checked}
        {...(checked ? { timeout: 1000 } : {})}
        collapsedHeight={50}
      >
        <div className={classes.container}>
          <h1 className={classes.title}>
            Welcome to <br />
            <span className={classes.colorText}>Bookeroo.</span>
          </h1>
          <Scroll to="book-list" smooth>
            <IconButton>
              <ExpandMoreIcon className={classes.goDown} />
            </IconButton>
          </Scroll>
        </div>
      </Collapse>
    </div>
  );
}

const styles = (theme: Theme) =>
  createStyles({
    root: {
      display: "flex",
      justifyContent: "center",
      alignItems: "center",
      height: "100vh",
      fontFamily: "Arial Rounded MT Bold",
    },
    appbar: {
      background: "white",
    },
    appbarWrapper: {
      width: "80%",
      margin: "0 auto",
    },
    appbarTitle: {
      flexGrow: 1,
    },
    icon: {
      color: "#F78888",
      fontSize: "2rem",
    },
    colorText: {
      color: "#5DA2D5",
    },
    container: {
      textAlign: "center",
    },
    title: {
      color: "#F8E9A1",
      fontSize: "4.5rem",
    },
    goDown: {
      color: "#A8D0E6",
      fontSize: "4rem",
    },
    navLogo: {
      marginRight: theme.spacing(3),
      width: 60,
      height: 60,
    },
    buttons: {
      "& button": {
        margin: theme.spacing(0, 1),
      },
    },
  });

type HeaderProps = WithStyles<typeof styles>;

export default withStyles(styles)(Header);
