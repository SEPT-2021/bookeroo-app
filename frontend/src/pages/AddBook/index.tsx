import React, { useState } from "react";
import { Redirect } from "react-router-dom";
import { useMutation } from "react-query";
import {
  Avatar,
  Box,
  createStyles,
  Grid,
  Theme,
  Typography,
  withStyles,
  WithStyles,
} from "@material-ui/core";
import { addBook } from "../../util/api";
import LoadingButton from "../../util/LoadingButton";
import FormField from "../../util/FormField";

function AddBook({ classes }: AddBookProps) {
  const [title, setTitle] = useState("");
  const [author, setAuthor] = useState("");
  const [isbn, setIsbn] = useState("");
  const [pageCount, setPageCount] = useState("");

  const { data, error, isSuccess, isLoading, mutate } = useMutation(addBook);

  // TODO debugging
  // eslint-disable-next-line no-console
  console.log(data);

  const onSubmit = async () => {
    mutate({ title, author, isbn, pageCount });
  };

  if (isSuccess) {
    return <Redirect to="/addBookSuccess" />;
  }

  return (
    <Grid container component="main" className={classes.root} justify="center">
      <div className={classes.paper}>
        <Avatar className={classes.avatar} />
        <Typography component="h1" variant="h5">
          Add A Book
        </Typography>
        <Box>
          <FormField
            errors={error?.response?.data}
            label="Book Title"
            name="title"
            autoComplete="title"
            onChange={setTitle}
          />
          <FormField
            errors={error?.response?.data}
            name="author"
            label="Author Name"
            autoComplete="firstName"
            onChange={setAuthor}
          />
          <FormField
            errors={error?.response?.data}
            name="isbn"
            label="ISBN"
            autoComplete="isbn"
            onChange={setIsbn}
          />
          <FormField
            errors={error?.response?.data}
            name="pageCount"
            label="Page Count"
            autoComplete="pageCount"
            onChange={setPageCount}
          />
          <LoadingButton
            loading={isLoading}
            fullWidth
            variant="contained"
            color="primary"
            className={classes.submit}
            onClick={onSubmit}
          >
            Add My Book
          </LoadingButton>

          <Box mt={5}>
            <Typography variant="caption">
              Disclaimer: This application was built for SEPT 2021, and is a
              university project. Please do not provide valuable data into this
              application.
            </Typography>
          </Box>
        </Box>
      </div>
    </Grid>
  );
}

const styles = (theme: Theme) =>
  createStyles({
    root: {
      height: "100vh",
    },
    steps: {
      "& img": {
        width: 230,
        margin: theme.spacing(6),
      },
      display: "flex",
      alignItems: "center",
      justifyContent: "center",
      flexDirection: "column",
    },
    paper: {
      margin: theme.spacing(8, 4),
      display: "flex",
      flexDirection: "column",
      alignItems: "center",
    },
    avatar: {
      margin: theme.spacing(1),
      backgroundColor: theme.palette.secondary.main,
    },
    submit: {
      margin: theme.spacing(3, 0, 2),
    },
  });

type AddBookProps = WithStyles<typeof styles>;

export default withStyles(styles)(AddBook);
