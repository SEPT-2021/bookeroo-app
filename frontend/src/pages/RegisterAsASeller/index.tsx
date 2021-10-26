import React, { useState } from "react";
import { Box, Grid, WithStyles, withStyles } from "@material-ui/core";
import { createStyles, Theme } from "@mui/material";
import { useMutation } from "react-query";
import FormField from "../../util/FormField";
import LoadingButton from "../../util/LoadingButton";
import { registerSeller } from "../../util/api";

function RegisterAsASeller({ classes }: RegisterAsASellerProps) {
  const [abn, setAbn] = useState("");
  const [businessName, setBusinessName] = useState("");
  const [businessPhone, setBusinessPhone] = useState("");

  const { isLoading, mutate, error } = useMutation(registerSeller);

  const onSubmit = () => mutate({ abn, businessName, businessPhone });

  return (
    <Grid container component="main" className={classes.root}>
      <Grid container spacing={2} justifyContent="center">
        <form className={classes.box} noValidate autoComplete="off">
          <Box>
            <FormField
              errors={error?.response?.data}
              id="outlined-secondary"
              name="abn"
              label="ABN"
              variant="outlined"
              color="secondary"
              onChange={setAbn}
            />
            <FormField
              errors={error?.response?.data}
              id="outlined-secondary"
              name="businessName"
              label="Business Name"
              variant="outlined"
              color="secondary"
              onChange={setBusinessName}
            />
            <FormField
              errors={error?.response?.data}
              id="outlined-secondary"
              name="businessPhone"
              label="Business Phone"
              variant="outlined"
              color="secondary"
              onChange={setBusinessPhone}
            />
            <LoadingButton
              loading={isLoading}
              variant="contained"
              color="primary"
              onClick={onSubmit}
            >
              Register As A Seller
            </LoadingButton>
          </Box>
        </form>
      </Grid>
    </Grid>
  );
}

const styles = (theme: Theme) =>
  createStyles({
    root: {
      height: "30vh",
      display: "flex",
      alignItems: "center",
      justifyContent: "center",
      flexDirection: "column",
      position: "absolute",
    },
    box: {
      "& > *": {
        margin: theme.spacing(1),
        width: "25ch",
      },
    },
  });

type RegisterAsASellerProps = WithStyles<typeof styles>;

export default withStyles(styles)(RegisterAsASeller);
