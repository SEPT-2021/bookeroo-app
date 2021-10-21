import React, { ChangeEvent, useState } from "react";
import { Redirect } from "react-router-dom";
import { useMutation } from "react-query";
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
  const [condition, setCondition] = useState("");
  const [category, setCategory] = useState("");
  const [openCategory, setOpenCategory] = React.useState(false);
  const [openCondition, setOpenCondition] = React.useState(false);

  const { error, isSuccess, isLoading, mutate } = useMutation(addBook);

  const onSubmit = async () => {
    mutate({
      title,
      author,
      pageCount,
      isbn,
      price,
      condition,
      category,
      description,
      coverFile,
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

  const uploadCoverFile = async (event: ChangeEvent<HTMLInputElement>) => {
    if (!event.target || !event.target.files) return;
    await setCoverFile(event.target.files[0]);
    await makeData();
  };

  const handleChangeBookCondition = (
    event: SelectChangeEvent<typeof condition>
  ) => {
    setCondition(event.target.value || "");
  };

  const handleChangeBookCategory = (
    event: SelectChangeEvent<typeof category>
  ) => {
    setCategory(event.target.value || "");
  };

  const handleClickOpenCondition = () => {
    setOpenCondition(true);
  };

  const handleClickOpenCategory = () => {
    setOpenCategory(true);
  };

  const handleCloseCondition = (
    event: React.SyntheticEvent<unknown>,
    reason?: string
  ) => {
    if (reason !== "backdropClick") {
      setOpenCondition(false);
    }
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
            <Button onClick={handleClickOpenCondition}>
              Select Book Condition
            </Button>
            <Dialog
              disableEscapeKeyDown
              open={openCondition}
              onClose={handleCloseCondition}
            >
              <DialogTitle>Fill the form</DialogTitle>
              <DialogContent>
                <Box
                  component="form"
                  sx={{ display: "flex", flexWrap: "wrap" }}
                >
                  <FormControl sx={{ m: 1, minWidth: 200 }}>
                    <InputLabel id="demo-dialog-select-label">
                      Book Condition
                    </InputLabel>
                    <Select
                      labelId="demo-dialog-select-label"
                      id="demo-dialog-select"
                      value={condition}
                      onChange={handleChangeBookCondition}
                      input={<OutlinedInput label="Book Condition" />}
                    >
                      <MenuItem value="">
                        <em>None</em>
                      </MenuItem>
                      <MenuItem value="NEW">New</MenuItem>
                      <MenuItem value="FINE">Fine</MenuItem>
                      <MenuItem value="VERY_GOOD">Very Good</MenuItem>
                      <MenuItem value="FAIR">Fair</MenuItem>
                      <MenuItem value="POOR">Poor</MenuItem>
                    </Select>
                  </FormControl>
                </Box>
              </DialogContent>
              <DialogActions>
                <Button onClick={handleCloseCondition}>Cancel</Button>
                <Button onClick={handleCloseCondition}>Ok</Button>
              </DialogActions>
            </Dialog>
          </div>

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
    bookCondition: {
      margin: "20px",
    },
  });

type AddBookProps = WithStyles<typeof styles>;

export default withStyles(styles)(AddBook);
