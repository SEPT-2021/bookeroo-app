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
  const bookSelection = false;
  const [title, setTitle] = useState("");
  const [author, setAuthor] = useState("");
  const [isbn, setIsbn] = useState("");
  const [pageCount, setPageCount] = useState("");
  const [coverFile, setCoverFile] = useState<File>();
  const [isCoverFilePicked, setIsCoverFilePicked] = useState(bookSelection);
  const [description, setDescription] = useState("");
  const [price, setPrice] = useState("");

  // WIP
  // eslint-disable-next-line @typescript-eslint/no-unused-vars
  const clearState = () => {
    setTitle("");
    setAuthor("");
    setAuthor("");
    setPageCount("");
    setIsCoverFilePicked(bookSelection);
    setDescription("");
    setPrice("");
  };

  // eslint-disable-next-line @typescript-eslint/no-unused-vars
  const { data, error, isSuccess, isLoading, mutate } = useMutation(addBook);

  const onSubmit = async () => {
    mutate({
      title,
      author,
      isbn,
      pageCount,
      coverFile,
      description,
      price,
    });
  };

  if (isSuccess) {
    return <Redirect to="/allBooks" />;
  }

  const makeData = async () => {
    if (isCoverFilePicked) {
      const formData = new FormData();
      formData.append("image", coverFile as File);
    }
  };

  const changeIsCoverFilePickedState = () => setIsCoverFilePicked(true);

  const uploadCoverFile = async (event: any) => {
    await setCoverFile(event.target.files[0]);
    await makeData();
  };

  /* const uploadCoverFile = (
    event: React.MouseEvent<HTMLInputElement, MouseEvent>
  ) => {
    const element = event.target as HTMLInputElement;
    element.value = "";
    console.log(element.files);
  }; */

  return (
    <Grid container component="main" className={classes.root} justifyContent="center">
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
          <FormField
            errors={error?.response?.data}
            name="price"
            label="Price"
            autoComplete="price"
            onChange={setPrice}
          />
          <FormField
            errors={error?.response?.data}
            name="description"
            label="Description"
            autoComplete="description"
            onChange={setDescription}
          />
          <div>
            <form encType="multipart/form-data" action="">
              <input
                type="file"
                name="file"
                onChange={uploadCoverFile}
                onClick={changeIsCoverFilePickedState}
              />
            </form>
          </div>

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
      marginTop: "50px",
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
