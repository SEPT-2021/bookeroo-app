import React, { useState } from "react";
import { createStyles, Theme } from "@material-ui/core/styles";
import { withStyles, WithStyles } from "@material-ui/core";
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
  const [data, setData] = useState<CartType>();
  const {
    data: checkoutData,
    mutate,
    error,
    isLoading,
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
    setData(makeData());
  };

  // const res = await axios.get("api/orders/checkout", { data });
  if (data) mutate(data);
  // eslint-disable-next-line no-console
  console.log(checkoutData);

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
        width: "25ch",
      },
    },
    button: {},
  });
};

type CheckOutProps = WithStyles<typeof styles>;

export default withStyles(styles)(CheckOut);
