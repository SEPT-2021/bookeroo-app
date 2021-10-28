import React, { useEffect, useState } from "react";
import { Redirect, useParams } from "react-router-dom";
import { useMutation, useQuery } from "react-query";
import {
  Avatar,
  Box,
  Button,
  createStyles,
  Dialog,
  DialogActions,
  DialogContent,
  DialogTitle,
  FormControl,
  Grid,
  InputLabel,
  MenuItem,
  OutlinedInput,
  Select,
  SelectChangeEvent,
  Theme,
  Typography,
} from "@mui/material";
import { withStyles, WithStyles } from "@material-ui/core";
import { Bookmark } from "@material-ui/icons";
import { deleteBookById, editBook, findBookById } from "../../util/api";
import LoadingButton from "../../util/LoadingButton";
import FormField from "../../util/FormField";
import LinearLoading from "../../util/LinearLoading";

function EditBook({ classes }: AddBookProps) {
  const { id } = useParams<{ id: string }>();
  const { data: book, isLoading: isBookLoading } = useQuery("editBook", () =>
    findBookById({ id })
  );

  const [title, setTitle] = useState("");
  const [author, setAuthor] = useState("");
  const [isbn, setIsbn] = useState("");
  const [pageCount, setPageCount] = useState("");
  const [description, setDescription] = useState("");
  const [category, setCategory] = useState("");
  const [openCategory, setOpenCategory] = useState(false);
  const {
    mutate: editMutate,
    isSuccess: isEditSuccess,
    error,
    isLoading: isEditLoading,
  } = useMutation(editBook);
  const {
    mutate: deleteMutate,
    isLoading: isDeleteLoading,
    isSuccess: isDeleteSuccess,
  } = useMutation(deleteBookById);

  useEffect(() => {
    if (book) {
      setTitle(book.title);
      setAuthor(book.author);
      setIsbn(book.isbn);
      setPageCount(book.pageCount);
      setDescription(book.description);
      setCategory(book.bookCategory);
      // eslint-disable-next-line no-console
      console.log(book);
    }
  }, [book]);

  const onSubmit = async () => {
    editMutate({
      id,
      title,
      author,
      pageCount,
      isbn,
      category,
      description,
    });
  };
  const isLoading = isDeleteLoading || isEditLoading;
  if (isEditSuccess) {
    return <Redirect to={`/book/${id}`} />;
  }
  if (isDeleteSuccess) {
    return <Redirect to="/allBooks" />;
  }
  if (isBookLoading) {
    return <LinearLoading />;
  }

  const handleChangeBookCategory = (
    event: SelectChangeEvent<typeof category>
  ) => {
    setCategory(event.target.value || "");
  };

  const handleClickOpenCategory = () => {
    setOpenCategory(true);
  };

  const handleCloseCategory = (
    event: React.SyntheticEvent<unknown>,
    reason?: string
  ) => {
    if (reason !== "backdropClick") {
      setOpenCategory(false);
    }
  };

  return (
    <Grid
      container
      component="main"
      className={classes.root}
      justifyContent="center"
    >
      <div className={classes.paper}>
        <Avatar className={classes.avatar}>
          <Bookmark />
        </Avatar>
        <Typography component="h1" variant="h5">
          Edit a Book
        </Typography>
        <Box>
          <FormField
            value={title}
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
            value={author}
          />
          <FormField
            errors={error?.response?.data}
            name="isbn"
            label="ISBN"
            autoComplete="isbn"
            onChange={setIsbn}
            value={isbn}
          />
          <FormField
            errors={error?.response?.data}
            name="pageCount"
            label="Page Count"
            autoComplete="pageCount"
            onChange={setPageCount}
            value={pageCount}
          />
          <FormField
            errors={error?.response?.data}
            name="description"
            label="Description"
            autoComplete="description"
            onChange={setDescription}
            value={description}
          />

          <div>
            <Button onClick={handleClickOpenCategory}>
              Select Book Category
            </Button>
            <Dialog
              disableEscapeKeyDown
              open={openCategory}
              onClose={handleCloseCategory}
            >
              <DialogTitle>Fill the form</DialogTitle>
              <DialogContent>
                <Box
                  component="form"
                  sx={{ display: "flex", flexWrap: "wrap" }}
                >
                  <FormControl sx={{ m: 1, minWidth: 200 }}>
                    <InputLabel id="demo-dialog-select-label">
                      Book Category
                    </InputLabel>
                    <Select
                      labelId="demo-dialog-select-label"
                      id="demo-dialog-select"
                      value={category}
                      onChange={handleChangeBookCategory}
                      input={<OutlinedInput label="Book Category" />}
                    >
                      <MenuItem value="">
                        <em>None</em>
                      </MenuItem>
                      <MenuItem value="LITERARY_FICTION">
                        Literary Fiction
                      </MenuItem>
                      <MenuItem value="MYSTERY">Mystery</MenuItem>
                      <MenuItem value="THRILLER">Thriller</MenuItem>
                      <MenuItem value="HORROR">Horror</MenuItem>
                      <MenuItem value="HISTORICAL">Historical</MenuItem>
                      <MenuItem value="ROMANCE">Romance</MenuItem>
                      <MenuItem value="WESTERN">Western</MenuItem>
                      <MenuItem value="BILDUNGSROMAN">Bildungsroman</MenuItem>
                      <MenuItem value="SPECULATIVE_FICTION">
                        Speculative Fiction
                      </MenuItem>
                      <MenuItem value="SCIENCE_FICTION">
                        Science Fiction
                      </MenuItem>
                      <MenuItem value="FANTASY">Fantasy</MenuItem>
                      <MenuItem value="DYSTOPIAN">Dystopian</MenuItem>
                      <MenuItem value="MAGICAL_REALISM">
                        Magical Realism
                      </MenuItem>
                      <MenuItem value="REALIST_LITERATURE">
                        Realist Literature
                      </MenuItem>
                      <MenuItem value="OTHER">Other</MenuItem>
                    </Select>
                  </FormControl>
                </Box>
              </DialogContent>
              <DialogActions>
                <Button onClick={handleCloseCategory}>Cancel</Button>
                <Button onClick={handleCloseCategory}>Ok</Button>
              </DialogActions>
            </Dialog>
          </div>

          <LoadingButton
            loading={isLoading}
            fullWidth
            variant="contained"
            color="primary"
            className={classes.submit}
            onClick={onSubmit}
          >
            Save Book
          </LoadingButton>
          <LoadingButton
            loading={isLoading}
            fullWidth
            variant="contained"
            color="secondary"
            onClick={() => deleteMutate({ id: String(id) })}
          >
            Delete Book
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
    bookCondition: {
      margin: "20px",
    },
  });

type AddBookProps = WithStyles<typeof styles>;

export default withStyles(styles)(EditBook);
