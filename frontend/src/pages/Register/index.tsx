import React, { useContext, useState } from "react";
import { Redirect } from "react-router-dom";
import { LockOutlined } from "@material-ui/icons";
import { useMutation } from "react-query";
import {
  Avatar,
  Box,
  createStyles,
  Grid,
  Hidden,
  Paper,
  Theme,
  Typography,
  withStyles,
  WithStyles,
} from "@material-ui/core";
import logo from "../../assets/logo.svg";
import Link from "../../util/Link";
import { registerUser } from "../../util/api";
import LoadingButton from "../../util/LoadingButton";
import FormField from "../../util/FormField";
import { GlobalContext } from "../../components/GlobalContext";

function Register({ classes }: RegisterProps) {
  const [username, setUsername] = useState("");
  const [firstName, setFirstName] = useState("");
  const [lastName, setLastName] = useState("");
  const [password, setPassword] = useState("");
  const [confirmPassword, setConfirmPassword] = useState("");
  const [addressLine1, setAddressLine1] = useState("");
  const [addressLine2, setAddressLine2] = useState("");
  const [city, setCity] = useState("");
  const [state, setState] = useState("");
  const [postalCode, setPostalCode] = useState("");
  const [phoneNumber, setPhoneNumber] = useState("");

  const context = useContext(GlobalContext);
  const passwordsMatchingError =
    password && password !== confirmPassword && "Passwords must match";
  const { data, error, isSuccess, isLoading, mutate } =
    useMutation(registerUser);

  const onSubmit = async () => {
    mutate({
      username,
      password,
      firstName,
      lastName,
      roles: "ROLE_USER",
      addressLine1,
      addressLine2,
      city,
      state,
      postalCode,
      phoneNumber,
      enabled: true,
    });
  };

  if (isSuccess && data) {
    context.login(data);
    return <Redirect to="/allBooks" />;
  }

  return (
    <Grid container component="main" className={classes.root}>
      <Grid item xs={12} sm={4} md={7} className={classes.steps}>
        <Box
          display="flex"
          alignItems="center"
          justifyContent="center"
          flexWrap="wrap"
          width="100%"
        >
          <img src={logo} alt="logo" />
          <Hidden xsDown>
            <div>
              <Typography variant="h1">Bookeroo</Typography>
              <Typography variant="h5">
                Dealing books has never been this easy
              </Typography>
            </div>
          </Hidden>
        </Box>
      </Grid>
      <Grid item xs={12} sm={8} md={5} component={Paper} elevation={6} square>
        <div className={classes.paper}>
          <Avatar className={classes.avatar}>
            <LockOutlined />
          </Avatar>
          <Typography component="h1" variant="h5">
            Register
          </Typography>
          <Box>
            <FormField
              errors={error?.response?.data}
              label="Email Address"
              name="username"
              autoComplete="email"
              onChange={setUsername}
            />
            <FormField
              errors={error?.response?.data}
              name="firstName"
              label="First Name"
              autoComplete="firstName"
              onChange={setFirstName}
            />
            <FormField
              errors={error?.response?.data}
              name="lastName"
              label="Last Name"
              autoComplete="lastName"
              onChange={setLastName}
            />
            <FormField
              errors={error?.response?.data}
              name="phoneNumber"
              label="Phone Number"
              autoComplete="phoneNumber"
              onChange={setPhoneNumber}
            />
            <FormField
              errors={error?.response?.data}
              name="password"
              label="Password"
              type="password"
              autoComplete="current-password"
              onChange={setPassword}
            />
            <FormField
              errors={
                passwordsMatchingError
                  ? {
                      confirmPassword: passwordsMatchingError,
                    }
                  : undefined
              }
              name="confirmPassword"
              label="Confirm Password"
              type="password"
              autoComplete="current-password"
              onChange={setConfirmPassword}
            />
            <FormField
              errors={error?.response?.data}
              name="addressLine1"
              label="Address Line 1"
              autoComplete="addressLine1"
              onChange={setAddressLine1}
            />
            <FormField
              errors={error?.response?.data}
              name="addressLine2"
              label="Address Line 2"
              autoComplete="addressLine2"
              onChange={setAddressLine2}
            />
            <FormField
              errors={error?.response?.data}
              name="city"
              label="City"
              autoComplete="city"
              onChange={setCity}
            />
            <FormField
              errors={error?.response?.data}
              name="state"
              label="State"
              autoComplete="state"
              onChange={setState}
            />
            <FormField
              errors={error?.response?.data}
              name="postalCode"
              label="Postal Code"
              autoComplete="postalCode"
              onChange={setPostalCode}
            />

            <LoadingButton
              loading={isLoading}
              disabled={!!passwordsMatchingError}
              fullWidth
              variant="contained"
              color="primary"
              className={classes.submit}
              onClick={onSubmit}
            >
              Register
            </LoadingButton>
            <Grid container>
              <Grid item>
                <Link to="/login">Have an account? Login Now</Link>
              </Grid>
            </Grid>
            <Box mt={5}>
              <Typography variant="caption">
                Disclaimer: This application was built for SEPT 2021, and is a
                university project. Please do not provide valuable data into
                this application.
              </Typography>
            </Box>
          </Box>
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
      margin: theme.spacing(15, 4),
      [theme.breakpoints.down("sm")]: {
        margin: theme.spacing(8, 4),
      },
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
  });

type RegisterProps = WithStyles<typeof styles>;

export default withStyles(styles)(Register);
