import React, { useContext } from "react";
import { makeStyles } from "@material-ui/core/styles";
import Card from "@material-ui/core/Card";
import CardContent from "@material-ui/core/CardContent";
import CardMedia from "@material-ui/core/CardMedia";
import Typography from "@material-ui/core/Typography";
import {
  Avatar,
  CardActions,
  CardHeader,
  CircularProgress,
  Collapse,
  IconButton,
} from "@material-ui/core";
import ExpandMoreIcon from "@material-ui/icons/ExpandMore";
import { AddShoppingCart, Book, Delete, Launch } from "@material-ui/icons";
import { useMutation, useQueryClient } from "react-query";
import { CardActionArea } from "@mui/material";
import { useHistory } from "react-router-dom";
import { BookItemType } from "../util/types";
import { GlobalContext, Role } from "./GlobalContext";
import { deleteBookById } from "../util/api";
import Link from "../util/Link";

const useStyles = makeStyles((theme) => ({
  card: {
    margin: theme.spacing(2),
  },
  media: {
    height: 250,
  },
}));

type BookProps = {
  book: BookItemType;
  checked: boolean;
  onCartClick: () => void;
};

export default function BookCard({
  book: { cover: imageUrl, id, title, description, author, pageCount, price },
  checked,
  onCartClick,
}: BookProps) {
  const classes = useStyles();
  const link = `/book/${id}`;
  const history = useHistory();
  const [expanded, setExpanded] = React.useState(false);
  const { user } = useContext(GlobalContext);
  const client = useQueryClient();
  const { mutate: deleteMutate, isLoading } = useMutation(deleteBookById, {
    onSuccess: () => {
      client.invalidateQueries("books");
    },
  });
  const handleExpandClick = () => {
    setExpanded(!expanded);
  };

  return (
    <Collapse in={checked} timeout={checked ? 1500 : undefined}>
      <Card className={classes.card}>
        <CardHeader
          avatar={
            <Avatar aria-label="recipe">
              <Book />
            </Avatar>
          }
          action={
            user?.role === Role.ROLE_ADMIN && (
              <IconButton onClick={() => deleteMutate({ id: String(id) })}>
                {isLoading ? (
                  <CircularProgress size={24} color="secondary" />
                ) : (
                  <Delete />
                )}
              </IconButton>
            )
          }
          title={<b>{title}</b>}
          subheader={author}
        />
        <CardActionArea onClick={() => history.push(link)}>
          <CardMedia className={classes.media} image={imageUrl} title={title} />
          <CardContent>
            <Typography variant="body2" color="textSecondary" component="p">
              Page Count: {pageCount}
              <br />
              Price: {price}
            </Typography>
          </CardContent>
        </CardActionArea>
        <CardActions disableSpacing>
          <IconButton aria-label="add to favorites" onClick={onCartClick}>
            <AddShoppingCart />
          </IconButton>
          <Link to={link}>
            <IconButton aria-label="share">
              <Launch />
            </IconButton>
          </Link>
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
