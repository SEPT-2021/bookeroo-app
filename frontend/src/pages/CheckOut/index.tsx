import * as React from "react";
import CssBaseline from "@mui/material/CssBaseline";
import Box from "@mui/material/Box";
import Container from "@mui/material/Container";
import Paper from "@mui/material/Paper";
import Button from "@mui/material/Button";
import Link from "@mui/material/Link";
import Typography from "@mui/material/Typography";
import { createTheme, ThemeProvider } from "@mui/material/styles";
import Grid from "@mui/material/Grid";
import FormControlLabel from "@mui/material/FormControlLabel";
import Checkbox from "@mui/material/Checkbox";
import { useState } from "react";
import { useMutation } from "react-query";
import { createStyles, withStyles } from "@material-ui/core";
import FormField from "../../util/FormField";
import { CartType, DataItemType } from "../../util/types";
import { checkout } from "../../util/api";

function Copyright() {
  return (
    <Typography variant="body2" color="text.secondary" align="center">
      {"Copyright © "}
      <Link color="inherit" href="https://material-ui.com/">
        Your Website
      </Link>{" "}
      {new Date().getFullYear()}.
    </Typography>
  );
}

const theme = createTheme();

function Checkout() {
  const [addressLine1, setAddressLine1] = useState("");
  const [addressLine2, setAddressLine2] = useState("");
  const [city, setCity] = useState("");
  const [state, setState] = useState("");
  const [postalCode, setPostalCode] = useState("");

  const { data: checkoutData, mutate, error, reset } = useMutation(checkout);

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
    return {
      orderItems: getLocal(),
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
    <ThemeProvider theme={theme}>
      <CssBaseline />
      <Container component="main" maxWidth="sm" sx={{ mb: 4 }}>
        <Paper
          variant="outlined"
          sx={{ my: { xs: 3, md: 6 }, p: { xs: 2, md: 3 } }}
        >
          <Typography component="h1" variant="h4" align="center">
            Checkout
          </Typography>
          <>
            <>
              <>
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
              </>
              <Box sx={{ display: "flex", justifyContent: "flex-end" }}>
                <Button
                  variant="contained"
                  onClick={onSubmit}
                  sx={{ mt: 3, ml: 1 }}
                >
                  Place Order With Paypal
                </Button>
              </Box>
            </>
          </>
        </Paper>
        <Copyright />
      </Container>
    </ThemeProvider>
  );
}

const styles = () => {
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
export default withStyles(styles)(Checkout);
