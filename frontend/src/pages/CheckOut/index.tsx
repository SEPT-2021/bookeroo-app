import React, { useState } from "react";
import { createStyles, Theme } from "@material-ui/core/styles";
import { Grid, withStyles, WithStyles } from "@material-ui/core";
import { useMutation } from "react-query";
import FormField from "../../util/FormField";
import LoadingButton from "../../util/LoadingButton";
import { checkout } from "../../util/api";
import { CartType, DataItemType } from "../../util/types";

function CheckOut({ classes }: CheckOutProps) {
  const [addressLine1, setAddressLine1] = useState("");
  const [addressLine2, setAddressLine2] = useState("");
  const [city, setCity] = useState("");
  const [state, setState] = useState("");
  const [postalCode, setPostalCode] = useState("");
  // eslint-disable-next-line @typescript-eslint/no-unused-vars
  const [data, setData] = useState<CartType>();

  const {
    data: checkoutData,
    mutate,
    // eslint-disable-next-line @typescript-eslint/no-unused-vars
    error,
    // eslint-disable-next-line @typescript-eslint/no-unused-vars
    isLoading,
    reset,
  } = useMutation(checkout);

  const getLocal = () => {
    return JSON.parse(localStorage.getItem("cart") as string) as DataItemType[];
  };

  const getShippingAddress = () => ({
    addressLine1,
    addressLine2,
    city,
    state,
    postalCode,
  });

  const makeData: () => CartType = () => {
    // eslint-disable-next-line @typescript-eslint/no-shadow
    return {
      orderItems: getLocal(),
      shippingAddress: getShippingAddress(),
    };
  };

  const onSubmit = () => {
    const newData = makeData();
    setData(newData);
    mutate(newData);
    // eslint-disable-next-line no-console
    console.log(newData);
  };
  if (checkoutData) {
    window.open(checkoutData);

    reset();
  }
  return (
    <div className={classes.root}>
      <form className={classes.root} noValidate autoComplete="off">
        <Grid
          container
          direction="row"
          justify="flex-start"
          alignItems="flex-start"
          spacing={1}
        >
          <Grid container spacing={1}>
            <Grid  item sm={2}/>
            <Grid item sm={6}>
            <FormField
              required
              errors={error?.response?.data}
              name="shippingAddress.addressLine1"
              label="Address Line 1"
              placeholder="Address Line 1"
              onChange={setAddressLine1}
              multiline
            />
            </Grid>
          </Grid>
          <FormField
            errors={error?.response?.data}
            name="shippingAddress.addressLine2"
            label="Address Line 2"
            placeholder="Address Line 2"
            onChange={setAddressLine2}
            multiline
          />
          <FormField
            required
            errors={error?.response?.data}
            name="shippingAddress.city"
            label="City"
            placeholder="City"
            onChange={setCity}
            multiline
          />
          <FormField
            required
            errors={error?.response?.data}
            name="shippingAddress.state"
            label="State"
            placeholder="State"
            onChange={setState}
            multiline
          />
          <FormField
            required
            errors={error?.response?.data}
            name="shippingAddress.postalCode"
            label="Postal Code"
            placeholder="Postal Code"
            onChange={setPostalCode}
            multiline
          />
          <div>
            <LoadingButton
              variant="contained"
              color="primary"
              onClick={onSubmit}
            >
              Submit
            </LoadingButton>
          </div>
        </Grid>
      </form>
    </div>
  );
}

const styles = (theme: Theme) => {
  return createStyles({
    root: {
      "& .MuiTextField-root": {
        margin: theme.spacing(1),
        width: "25ch",
        marginTop: "100px",
      },
    },
    button: {},
  });
};

type CheckOutProps = WithStyles<typeof styles>;

export default withStyles(styles)(CheckOut);
