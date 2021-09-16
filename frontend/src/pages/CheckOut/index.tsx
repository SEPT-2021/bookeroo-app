import React, { useState } from "react";
import TextField from "@material-ui/core/TextField";
import { createStyles, makeStyles, Theme } from "@material-ui/core/styles";
import { Button, withStyles, WithStyles } from "@material-ui/core";
import { BookItemType } from "../Books";
import FormField from "../../util/FormField";
import LoadingButton from "../../util/LoadingButton";

type ShippingItemType = {
  addressLine1: string;
  addressLine2: string;
  city: string;
  state: string;
  postalCode: string;
};

type ForBackEndType = {
  orderItems: [];
  book: BookItemType;
  shippingAddress: ShippingItemType;
};

function CheckOut({ classes }: CheckOutProps) {
  const [addressLine1, setAddressLine1] = useState("");
  const [addressLine2, setAddressLine2] = useState("");
  const [city, setCity] = useState("");
  const [state, setState] = useState("");
  const [postalCode, setPostalCode] = useState("");

  const getLocal = () => {
    return JSON.parse(localStorage.getItem("cart") as string);
  };
  

  const getShippingAddress = () => {
    const initial = Array<ShippingItemType>();
    initial.push({ addressLine1, addressLine2, city, state, postalCode });
    return Array.from(initial);
  };

  const makeData = () => {
    // eslint-disable-next-line @typescript-eslint/no-shadow
    return {
      ...getLocal().map((obj: BookItemType) => ({
        book: obj

      })
    ,};
  };

  const onSubmit = () => {
    makeData();
    console.log(makeData());
  };

  return (
    <div className={classes.root}>
      <form className={classes.root} noValidate autoComplete="off">
        <div>
          <FormField
            required
            name="addressLine1"
            label="Address Line 1"
            placeholder="Address Line 1"
            onChange={setAddressLine1}
            multiline
          />
          <FormField
            name="addressLine2"
            label="Address Line 2"
            placeholder="Address Line 2"
            onChange={setAddressLine2}
            multiline
          />
          <FormField
            required
            name="city"
            label="City"
            placeholder="City"
            onChange={setCity}
            multiline
          />
          <FormField
            required
            name="state"
            label="State"
            placeholder="State"
            onChange={setState}
            multiline
          />
          <FormField
            required
            name="postalCode"
            label="Postal Code"
            placeholder="Postal Code"
            onChange={setPostalCode}
            multiline
          />
          <LoadingButton variant="contained" color="primary" onClick={onSubmit}>
            Submit
          </LoadingButton>
        </div>
      </form>
    </div>
  );
}

const styles = (theme: Theme) => {
  return createStyles({
    root: {
      "& .MuiTextField-root": {
        margin: theme.spacing(1),
        width: "25ch"
      }
    },
    button: {}
  });
};

type CheckOutProps = WithStyles<typeof styles>;

export default withStyles(styles)(CheckOut);
