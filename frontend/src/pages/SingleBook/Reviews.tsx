import React from "react";
import { makeStyles } from "@material-ui/core/styles";
import {
  List,
  ListItem,
  Divider,
  ListItemText,
  ListItemAvatar,
  Avatar,
  Typography,
  Box,
} from "@material-ui/core";
import { Rating } from "@mui/material";

const useStyles = makeStyles((theme) => ({
  root: {
    width: "100%",
    backgroundColor: theme.palette.background.paper,
  },
  fonts: {
    fontWeight: "bold",
  },
  inline: {
    display: "inline",
  },
}));
interface Review {
  id: string;
  text: string;
  userFullName: string;
  rating: number;
  userId: string;
}

// Based on: https://github.com/gunasai/material-ui-comments/blob/master/src/components/Comment.js
export default function Reviews({ reviews }: { reviews: Review[] }) {
  const classes = useStyles();
  return (
    <List className={classes.root}>
      {reviews.map((review) => (
        <React.Fragment key={review.id}>
          <ListItem key={review.id} alignItems="flex-start">
            <ListItemAvatar>
              <Avatar />
            </ListItemAvatar>
            <ListItemText
              primary={
                <Box display="flex" alignItems="center">
                  <Typography className={classes.fonts}>
                    {review.userFullName}
                  </Typography>
                  <Rating
                    readOnly
                    value={review.rating}
                    sx={{ marginLeft: 3 }}
                  />
                </Box>
              }
              secondary={review.text}
            />
          </ListItem>
          <Divider />
        </React.Fragment>
      ))}
    </List>
  );
}
