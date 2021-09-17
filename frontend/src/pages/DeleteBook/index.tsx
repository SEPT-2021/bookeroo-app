import React, { useState } from "react";
import { createStyles, Theme } from "@material-ui/core/styles";
import { Box, Grid, withStyles, WithStyles } from "@material-ui/core";
import { useMutation } from "react-query";
import SearchBar from "../../components/searchBar";
import { deleteBookById } from "../../util/api";
import FormField from "../../util/FormField";
import LoadingButton from "../../util/LoadingButton";

/**
 * @deprecated
 */
function DeleteBook({ classes }: DeleteBookProps) {
  const [id, setId] = useState("");

  const { isLoading, mutate, error, data, isSuccess } =
    useMutation(deleteBookById);
  const onSubmit = () => mutate({ id });

  // TODO testing
  if (isSuccess) {
    // eslint-disable-next-line no-console
    console.log("DELETED BOOK SUCCESS");
    // eslint-disable-next-line no-console
    console.log(data);
  }

  return (
    <Grid container component="main" className={classes.root}>
      <Grid item xs={12}>
        <SearchBar />
      </Grid>
      <Grid container spacing={2} justify="center">
        <div
          style={{
            display: "flex",
            justifyContent: "center",
            alignItems: "center",
            height: "100vh",
          }}
        >
          <form className={classes.box} noValidate autoComplete="off">
            <Box>
              <FormField
                errors={error?.response?.data}
                id="outlined-secondary"
                name="id"
                label="Book ID"
                variant="outlined"
                color="secondary"
                onChange={setId}
              />
              <LoadingButton
                loading={isLoading}
                variant="contained"
                color="primary"
                onClick={onSubmit}
              >
                Delete My Book!
              </LoadingButton>
            </Box>
          </form>
        </div>
      </Grid>
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
    box: {
      "& > *": {
        margin: theme.spacing(1),
        width: "25ch",
      },
    },
    avatar: {
      margin: theme.spacing(1),
      backgroundColor: theme.palette.secondary.main,
    },
    submit: {
      margin: theme.spacing(3, 0, 2),
    },
    button: {
      margin: theme.spacing(1),
    },
  });

type DeleteBookProps = WithStyles<typeof styles>;

export default withStyles(styles)(DeleteBook);
