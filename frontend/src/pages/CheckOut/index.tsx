import * as React from "react";
import { useContext, useState } from "react";
import Grid from "@mui/material/Grid";
import FormControlLabel from "@mui/material/FormControlLabel";
import Checkbox from "@mui/material/Checkbox";
import { useMutation } from "react-query";
import { Box, Container, Paper, Typography } from "@mui/material";
import { Button } from "@material-ui/core";
import FormField from "../../util/FormField";
import { CartType } from "../../util/types";
import { checkout } from "../../util/api";
import { GlobalContext } from "../../components/GlobalContext";
import { Cart } from "../../components/DrawerCart";
import Link from "../../util/Link";

function Copyright() {
  return (
    <Typography variant="body2" color="text.secondary" align="center">
      {"Copyright © "}
      <Link color="inherit" to="/">
        Bookeroo
      </Link>{" "}
      {new Date().getFullYear()}.
    </Typography>
  );
}

function Checkout() {
  const { cartItems, user: userData } = useContext(GlobalContext);
  const [addressLine1, setAddressLine1] = useState(userData?.addressLine1);
  const [addressLine2, setAddressLine2] = useState(userData?.addressLine2);
  const [city, setCity] = useState(userData?.city);
  const [state, setState] = useState(userData?.state);
  const [postalCode, setPostalCode] = useState(userData?.postalCode);
  const getItems = () => {
    return cartItems.map((item) => item.listing);
  };
  const { data: checkoutData, mutate, error, reset } = useMutation(checkout);

  const getShippingAddress = () => ({
    addressLine1,
    addressLine2,
    city,
    state,
    postalCode,
  });

  const makeData: () => CartType = () => {
    return {
      orderItems: getItems(),
      shippingAddress: getShippingAddress(),
    };
  };

  const onSubmit = () => {
    const newData = makeData();
    mutate(newData);
  };
  if (checkoutData) {
    window.open(checkoutData);
    reset();
  }

  return (
    <Grid container style={{ marginTop: 120 }}>
      <Grid item md={6}>
        <Box
          sx={{
            "& .MuiTextField-root": {
              margin: 1,
              marginTop: "10px",
            },
            marginTop: "80px",
            display: "flex",
            justifyContent: "center",
            alignItems: "center",
            height: "70vh",
          }}
        >
          <Container component="main" maxWidth="sm" sx={{ mb: 4 }}>
            <Paper
              variant="outlined"
              sx={{ my: { xs: 3, md: 6 }, p: { xs: 2, md: 3 } }}
            >
              <Typography component="h1" variant="h4" align="center">
                Checkout
              </Typography>
              <Typography variant="h6" gutterBottom>
                Shipping address
              </Typography>
              <Grid container spacing={3}>
                <Grid item xs={12} sm={6}>
                  <FormField
                    required
                    id="firstName"
                    name="firstName"
                    label="First name"
                    fullWidth
                    autoComplete="given-name"
                    variant="standard"
                    value={userData?.firstName}
                  />
                </Grid>
                <Grid item xs={12} sm={6}>
                  <FormField
                    required
                    id="lastName"
                    name="lastName"
                    label="Last name"
                    fullWidth
                    autoComplete="family-name"
                    variant="standard"
                    value={userData?.lastName}
                  />
                </Grid>
                <Grid item xs={12}>
                  <FormField
                    required
                    errors={error?.response?.data}
                    id="address1"
                    name="shippingAddress.addressLine1"
                    label="Address line 1"
                    fullWidth
                    autoComplete="shipping address-line1"
                    variant="standard"
                    value={addressLine1}
                    onChange={setAddressLine1}
                  />
                </Grid>
                <Grid item xs={12}>
                  <FormField
                    id="address2"
                    errors={error?.response?.data}
                    name="shippingAddress.addressLine2"
                    label="Address line 2"
                    fullWidth
                    autoComplete="shipping address-line2"
                    variant="standard"
                    value={addressLine2}
                    onChange={setAddressLine2}
                  />
                </Grid>
                <Grid item xs={12} sm={6}>
                  <FormField
                    required
                    errors={error?.response?.data}
                    name="shippingAddress.city"
                    label="City"
                    fullWidth
                    autoComplete="shipping address-level2"
                    variant="standard"
                    value={city}
                    onChange={setCity}
                  />
                </Grid>
                <Grid item xs={12} sm={6}>
                  <FormField
                    id="state"
                    errors={error?.response?.data}
                    name="shippingAddress.state"
                    label="State/Province/Region"
                    fullWidth
                    variant="standard"
                    value={state}
                    onChange={setState}
                  />
                </Grid>
                <Grid item xs={12} sm={6}>
                  <FormField
                    required
                    id="zip"
                    errors={error?.response?.data}
                    name="shippingAddress.postalCode"
                    label="Post Code"
                    fullWidth
                    autoComplete="shipping postal-code"
                    variant="standard"
                    value={postalCode}
                    onChange={setPostalCode}
                  />
                </Grid>
                <Grid item xs={12}>
                  <FormControlLabel
                    control={
                      <Checkbox
                        color="secondary"
                        name="saveAddress"
                        value="yes"
                      />
                    }
                    label="Use this address for payment details"
                  />
                </Grid>
              </Grid>

              <Box sx={{ display: "flex", justifyContent: "flex-end" }}>
                <Button
                  variant="contained"
                  onClick={onSubmit}
                  style={{ marginTop: 45, marginLeft: 15 }}
                >
                  Place Order With Paypal
                </Button>
              </Box>
            </Paper>
            <Copyright />
          </Container>
        </Box>
      </Grid>
      <Grid item md={6}>
        <Cart hideCheckoutButton />
      </Grid>
    </Grid>
  );
}

export default Checkout;
