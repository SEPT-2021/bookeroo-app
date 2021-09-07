import React from "react";
import { makeStyles } from "@material-ui/core/styles";
import Card from "@material-ui/core/Card";
import CardContent from "@material-ui/core/CardContent";
import CardMedia from "@material-ui/core/CardMedia";
import Typography from "@material-ui/core/Typography";
import { Collapse } from "@material-ui/core";

const useStyles = makeStyles({
  root: {
    maxWidth: 645,
    background: "rgba(0,0,0,0.5)",
    margin: "20px",
  },
  media: {
    height: 440,
  },
  title: {
    fontFamily: "Nunito",
    fontWeight: "bold",
    fontSize: "2rem",
    color: "#fff",
  },

  description: {
    fontFamily: "Nunito",
    fontSize: "1.1rem",
    color: "#ddd",
  },
});

type BookProps = {
  imageUrl: string;
  title: string;
  description: string;
  checked: boolean;
};

export default function Image({
  imageUrl,
  title,
  description,
  checked,
}: BookProps) {
  const classes = useStyles();

  return (
    <Collapse
      in={checked}
      /* eslint-disable react/jsx-props-no-spreading */
      {...(checked ? { timeout: 1000 } : {})}
    >
      <Card className={classes.root}>
        <CardMedia
          className={classes.media}
          image={imageUrl}
          title={classes.title}
        />
        <CardContent>
          <Typography
            gutterBottom
            variant="h5"
            component="h1"
            className={classes.title}
          >
            {title}
          </Typography>
          <Typography
            variant="body2"
            color="textSecondary"
            component="p"
            className={classes.description}
          >
            {description}
          </Typography>
        </CardContent>
      </Card>
    </Collapse>
  );
}
