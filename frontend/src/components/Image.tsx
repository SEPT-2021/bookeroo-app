import React from "react";
import { makeStyles } from "@material-ui/core/styles";
import Card from "@material-ui/core/Card";
import CardContent from "@material-ui/core/CardContent";
import CardMedia from "@material-ui/core/CardMedia";
import Typography from "@material-ui/core/Typography";
import {
  Avatar,
  CardActions,
  CardHeader,
  Collapse,
  IconButton,
} from "@material-ui/core";
import ExpandMoreIcon from "@material-ui/icons/ExpandMore";
import {
  BookmarkRounded,
  Favorite,
  LinkOutlined,
  Share,
} from "@material-ui/icons";
import type { Book } from "../static/books";
import { BOOK_ANIMATION_TIME } from "../static/books";

const useStyles = makeStyles((theme) => ({
  card: {
    margin: theme.spacing(2),
  },
  media: {
    height: 250,
  },
}));

type BookProps = {
  book: Book;
  checked: boolean;
};

export default function Image({
  book: { imageUrl, title, description },
  checked,
}: BookProps) {
  const classes = useStyles();
  const [expanded, setExpanded] = React.useState(false);

  const handleExpandClick = () => {
    setExpanded(!expanded);
  };
  return (
    <Collapse in={checked} timeout={checked ? BOOK_ANIMATION_TIME : undefined}>
      <Card className={classes.card}>
        <CardHeader
          avatar={
            <Avatar aria-label="recipe">
              <BookmarkRounded />
            </Avatar>
          }
          action={
            <IconButton aria-label="settings">
              <LinkOutlined />
            </IconButton>
          }
          title={title}
          subheader="Author Name"
        />
        <CardMedia className={classes.media} image={imageUrl} title={title} />
        <CardContent>
          <Typography variant="body2" color="textSecondary" component="p">
            A nice book.
          </Typography>
        </CardContent>
        <CardActions disableSpacing>
          <IconButton aria-label="add to favorites">
            <Favorite />
          </IconButton>
          <IconButton aria-label="share">
            <Share />
          </IconButton>
          <IconButton
            onClick={handleExpandClick}
            aria-expanded={expanded}
            aria-label="show more"
          >
            <ExpandMoreIcon />
          </IconButton>
        </CardActions>
        <Collapse in={expanded} timeout="auto" unmountOnExit>
          <CardContent>
            <Typography paragraph>{description}</Typography>
          </CardContent>
        </Collapse>
      </Card>
    </Collapse>
  );
}
