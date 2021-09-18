import React, { useState } from "react";
import { createStyles, Theme } from "@material-ui/core/styles";
import { Box, Grid, withStyles, WithStyles } from "@material-ui/core";
import { useMutation } from "react-query";
import { getBookByType } from "../../util/api";
import FormField from "../../util/FormField";
import LoadingButton from "../../util/LoadingButton";

function BookSearchType({ classes }: BookSearchTypeProps) {
  const [searchTerm, setSearchTerm] = useState("");
  const [type, setType] = useState("");

  const { isLoading, mutate, error, data, isSuccess } =
    useMutation(getBookByType);
  const onSubmit = () => mutate({ searchTerm, type });

  // TODO testing
  if (isSuccess) {
    // eslint-disable-next-line no-console
    console.log(data);
  }

  return (
    <Grid container component="main" className={classes.root}>
      <Grid container spacing={2} justifyContent="center">
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
                name="searchTerm"
                label="Search For"
                variant="outlined"
                color="secondary"
                onChange={setSearchTerm}
              />
              <FormField
                errors={error?.response?.data}
                id="outlined-secondary"
                name="type"
                label="Title / Author / Keyword"
                variant="outlined"
                color="secondary"
                onChange={setType}
              />
              <LoadingButton
                loading={isLoading}
                variant="contained"
                color="primary"
                onClick={onSubmit}
              >
                Find My Book!
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

type BookSearchTypeProps = WithStyles<typeof styles>;

export default withStyles(styles)(BookSearchType);
