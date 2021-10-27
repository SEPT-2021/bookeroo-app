import React, { useContext, useEffect, useState } from "react";

import { useMutation } from "react-query";
import {
  Box,
  createStyles,
  Grid,
  withStyles,
  WithStyles,
} from "@material-ui/core";
import { updateUser } from "../../../util/api";
import LoadingButton from "../../../util/LoadingButton";
import FormField from "../../../util/FormField";
import { GlobalContext } from "../../GlobalContext";
import theme from "../../../theme";

function UserDetails({ classes }: UserDetailsProps) {
  const { user } = useContext(GlobalContext);
  const { isLoading, mutate, isSuccess, error } = useMutation(updateUser);

  const [firstName, setFirstName] = useState("");
  const [lastName, setLastName] = useState("");
  const [addressLine1, setAddressLine1] = useState("");
  const [addressLine2, setAddressLine2] = useState("");
  const [city, setCity] = useState("");
  const [state, setState] = useState("");
  const [postalCode, setPostalCode] = useState("");

  useEffect(() => {
    if (!user) return;
    setFirstName(user?.firstName);
    setLastName(user?.lastName);
    setAddressLine1(user?.addressLine1);
    setAddressLine2(user?.addressLine2);
    setCity(user?.city);
    setState(user?.state);
    setPostalCode(user?.postalCode);
  }, [user]);

  const onSubmit = async () => {
    mutate({
      firstName,
      lastName,
      addressLine1,
      addressLine2,
      city,
      state,
      postalCode,
    });
  };

  if (isSuccess) {
    window.location.reload(false);
  }

  return (
    <Grid container component="main" className={classes.root}>
      <Grid container spacing={1} justifyContent="center">
        <Box mt={2}>
          <FormField
            errors={error?.response?.data}
            name="firstName"
            label="First Name"
            defaultValue={user?.firstName}
            autoComplete={user?.firstName}
            onChange={setFirstName}
          />
          <FormField
            errors={error?.response?.data}
            name="lastName"
            label="Last Name"
            defaultValue={user?.lastName}
            autoComplete={user?.lastName}
            onChange={setLastName}
          />
          <FormField
            errors={error?.response?.data}
            name="addressLine1"
            label="Address Line 1"
            defaultValue={user?.addressLine1}
            autoComplete={user?.addressLine1}
            onChange={setAddressLine1}
          />
          <FormField
            errors={error?.response?.data}
            name="addressLine2"
            label="Address Line 2"
            defaultValue={user?.addressLine2}
            autoComplete={user?.addressLine2}
            onChange={setAddressLine2}
          />
          <FormField
            errors={error?.response?.data}
            name="city"
            label="City"
            defaultValue={user?.city}
            autoComplete={user?.city}
            onChange={setCity}
          />
          <FormField
            errors={error?.response?.data}
            name="state"
            label="State"
            defaultValue={user?.state}
            autoComplete={user?.state}
            onChange={setState}
          />
          <FormField
            errors={error?.response?.data}
            name="postalCode"
            label="Postal code"
            defaultValue={user?.postalCode}
            autoComplete={user?.postalCode}
            onChange={setPostalCode}
          />

          <LoadingButton
            loading={isLoading}
            fullWidth
            variant="contained"
            color="primary"
            onClick={onSubmit}
          >
            {" "}
            update{" "}
          </LoadingButton>
        </Box>
      </Grid>
    </Grid>
  );
}

const styles = () =>
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

type UserDetailsProps = WithStyles<typeof styles>;

export default withStyles(styles)(UserDetails);
