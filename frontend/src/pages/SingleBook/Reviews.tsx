import React, { useContext, useState } from "react";
import { makeStyles } from "@material-ui/core/styles";
import {
  Avatar,
  Box,
  Divider,
  List,
  ListItem,
  ListItemAvatar,
  ListItemText,
  Typography,
} from "@material-ui/core";
import { Rating } from "@mui/material";
import { useMutation, useQueryClient } from "react-query";
import { Review } from "../../util/types";
import FormField from "../../util/FormField";
import { reviewBook } from "../../util/api";
import LoadingButton from "../../util/LoadingButton";
import { GlobalContext } from "../../components/GlobalContext";

const useStyles = makeStyles((theme) => ({
  root: {
    margin: theme.spacing(3, 0),
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

// Based on: https://github.com/gunasai/material-ui-comments/blob/master/src/components/Comment.js
export default function Reviews({
  reviews,
  bookId,
}: {
  reviews: Review[];
  bookId: string;
}) {
  const classes = useStyles();
  const { user } = useContext(GlobalContext);
  const [reviewText, setReviewText] = useState("");
  const client = useQueryClient();
  const { error, isLoading, isSuccess, mutate } = useMutation(reviewBook, {
    onSuccess: () => {
      client.invalidateQueries("singleBook");
    },
  });
  const [rating, setRating] = useState(0);
  return (
    <>
      {isSuccess ? (
        <Typography>Review submitted!</Typography>
      ) : (
        user && (
          <>
            <Typography>Write your review</Typography>
            <Rating
              value={rating}
              onChange={(_, newRating) => setRating(newRating || 0)}
            />
            <FormField
              errors={error?.response?.data}
              name="text"
              value={reviewText}
              onChange={setReviewText}
            />

            <br />
            <LoadingButton
              loading={isLoading}
              onClick={() => mutate({ rating, text: reviewText, id: bookId })}
              disabled={!rating || !reviewText}
              variant="contained"
              color="primary"
            >
              Submit Review
            </LoadingButton>
          </>
        )
      )}
      {reviews.length ? (
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
      ) : (
        <Typography variant="h6">No Reviews yet!</Typography>
      )}
    </>
  );
}
