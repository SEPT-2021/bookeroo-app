import React, { useEffect, useState } from "react";
import {
  Button,
  Collapse,
  createStyles,
  IconButton,
  withStyles,
  WithStyles,
} from "@material-ui/core";
import ExpandMoreIcon from "@material-ui/icons/ExpandMore";
import { Link as Scroll } from "react-scroll";
import TextLoop from "react-text-loop";
import Link from "../../util/Link";

function Header({ classes }: HeaderProps) {
  const [checked, setChecked] = useState(false);
  useEffect(() => {
    setChecked(true);
  }, []);
  return (
    <div className={classes.root} id="header">
      <Collapse
        in={checked}
        {...(checked ? { timeout: 1000 } : {})}
        collapsedHeight={50}
      >
        <div className={classes.container}>
          <h1 className={classes.title}>
            Welcome to <br />
            <span className={classes.colorText}>Bookeroo</span>
            <TextLoop
              interval={1000}
              fade
              springConfig={{ stiffness: 200, damping: 5 }}
            >
              <span> . </span>
              <span> ! </span>
            </TextLoop>
          </h1>
          <Link to="/allBooks">
            <Button variant="contained" color="primary">
              View our Collection
            </Button>
          </Link>
          <br />
          <Scroll to="contact" smooth>
            <IconButton>
              <ExpandMoreIcon className={classes.goDown} />
            </IconButton>
          </Scroll>
        </div>
      </Collapse>
    </div>
  );
}

const styles = () =>
  createStyles({
    root: {
      display: "flex",
      justifyContent: "center",
      alignItems: "center",
      height: "100vh",
      fontFamily: "Arial Rounded MT Bold",
    },
    colorText: {
      color: "#5DA2D5",
    },
    icon: {
      color: "#F78888",
      fontSize: "2rem",
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
  });

type HeaderProps = WithStyles<typeof styles>;

export default withStyles(styles)(Header);
