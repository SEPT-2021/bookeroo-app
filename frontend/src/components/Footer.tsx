import React from "react";
import { createStyles, withStyles, WithStyles } from "@material-ui/core";

function Footer({ classes }: FooterProps) {
  return (
    <div className={classes.footer}>
      <footer>
        <p> 2021 © SEPT Bookeroo. All rights reserved.</p>
      </footer>
    </div>
  );
}

const styles = () =>
  createStyles({
    footer: {
      padding: "10px 20px",
      background: "#666",
      color: "white",
      bottom: "0",
      width: "100%",
      textAlign: "center",
    },
  });

type FooterProps = WithStyles<typeof styles>;

export default withStyles(styles)(Footer);
