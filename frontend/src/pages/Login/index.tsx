import React, { useState } from "react";
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
import { loginUser } from "../../util/api";
import LoadingButton from "../../util/LoadingButton";
import FormField from "../../util/FormField";

function Login({ classes }: LoginProps) {
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");

  const { isLoading, mutate, error, data, isSuccess } = useMutation(loginUser);
  const onSubmit = () => mutate({ username, password });
  // TODO testing
  // eslint-disable-next-line no-console
  console.log(data);
  if (isSuccess) {
    return <Redirect to="/loginSuccess" />;
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
            Sign in
          </Typography>
          <Box>
            <FormField
              errors={error?.response?.data}
              name="username"
              autoComplete="email"
              label="Email Address"
              value={username}
              onChange={setUsername}
            />
            <FormField
              errors={error?.response?.data}
              name="password"
              label="Password"
              type="password"
              autoComplete="current-password"
              onChange={setPassword}
            />
            <LoadingButton
              type="submit"
              loading={isLoading}
              fullWidth
              variant="contained"
              color="primary"
              className={classes.submit}
              onClick={onSubmit}
            >
              Sign In
            </LoadingButton>
            <Grid container>
              <Grid item xs>
                <Link to="/forgot-password" variant="body2">
                  Forgot password?
                </Link>
              </Grid>
              <Grid item>
                <Link to="/register" variant="body2">
                  Don't have an account? Register
                </Link>
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
      margin: theme.spacing(8, 4),
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

type LoginProps = WithStyles<typeof styles>;

export default withStyles(styles)(Login);
