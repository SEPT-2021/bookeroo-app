import React from "react";
import { createStyles, Grid, withStyles, WithStyles } from "@material-ui/core";
import Animate from "@charlesvien/react-animatecss";
import ImageAvatars from "../../components/Avatars";

function AboutUs({ classes }: AboutUsProps) {
  return (
    <Grid container component="main" className={classes.root}>
      <h1>
        <Animate
          animationIn="fadeIn"
          animationOut="fadeOut"
          inDuration={4000}
          outDuration={2000}
          visible
        >
          Who Are We ?
        </Animate>
      </h1>

      <Animate
        animationIn="fadeIn"
        animationOut="fadeOut"
        inDuration={2000}
        outDuration={3000}
        visible
      >
        <ImageAvatars />
      </Animate>
    </Grid>
  );
}

const styles = () =>
  createStyles({
    root: {
      display: "flex",
      alignItems: "center",
      justifyContent: "center",
      marginTop: "100px",
      height: "100vh",
    },
  });

type AboutUsProps = WithStyles<typeof styles>;

export default withStyles(styles)(AboutUs);
