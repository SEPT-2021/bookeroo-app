import React from "react";
import { makeStyles, createStyles, Theme } from "@material-ui/core/styles";
import Avatar from "@material-ui/core/Avatar";
import person1 from "../../assets/Profile/p1.jpg";
import person2 from "../../assets/Profile/p2.jpg";
import person3 from "../../assets/Profile/p3.jpg";
import person4 from "../../assets/Profile/p4.jpg";

const useStyles = makeStyles((theme: Theme) =>
  createStyles({
    root: {
      display: "flex",
      "& > *": {
        margin: theme.spacing(1),
      },
    },
    small: {
      width: theme.spacing(3),
      height: theme.spacing(3),
    },
    large: {
      width: theme.spacing(7),
      height: theme.spacing(7),
    },
  })
);

export default function ImageAvatars() {
  const classes = useStyles();

  return (
    <div className={classes.root}>
      <Avatar alt="P1" src={person1} className={classes.large} />
      <Avatar alt="P2" src={person2} className={classes.large} />
      <Avatar alt="P3" src={person3} className={classes.large} />
      <Avatar alt="P4" src={person4} className={classes.large} />
    </div>
  );
}
