import React, { useState } from "react";
import axios from "axios";
import {
  Avatar,
  Box,
  Button,
  Checkbox,
  createStyles,
  FormControlLabel,
  Grid,
  Hidden,
  Link,
  Paper,
  TextField,
  Theme,
  Typography,
  withStyles,
  WithStyles,
} from "@material-ui/core";
import { LockOutlined } from "@material-ui/icons";
import logo from "../../assets/logo.svg";

function Register({ classes }: RegisterProps) {
  const [Username, setUsername] = useState("");
  const [FirstName, setFirstName] = useState("");
  const [LastName, setLastName] = useState("");
  const [Password, setPassword] = useState("");
  const [ConfirmPassword, setConfirmPassword] = useState("");

  const registerDetails = {
    username: Username,
    firstName: FirstName,
    lastName: LastName,
    password: Password,
    confirmPassword: ConfirmPassword,
  };

  async function registerPost() {
    axios({
      method: "POST",
      url: "/api/users/register",
      data: { registerDetails },
    }).then(
      (response) => {
        // eslint-disable-next-line no-console
        return response.data.json;
      },
      (error) => {
        // eslint-disable-next-line no-console
        console.log(error);
      }
    );
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
            <TextField
              variant="outlined"
              margin="normal"
              required
              fullWidth
              id="email"
              label="Email Address"
              name="email"
              autoComplete="email"
              autoFocus
              onChange={(e) => setUsername(e.target.value)}
            />
            <TextField
              variant="outlined"
              margin="normal"
              required
              fullWidth
              name="firstName"
              label="First Name"
              id="firstName"
              autoComplete="firstName"
              onChange={(e) => setFirstName(e.target.value)}
            />
            <TextField
              variant="outlined"
              margin="normal"
              required
              fullWidth
              name="lastName"
              label="Last Name"
              id="lastName"
              autoComplete="lastName"
              onChange={(e) => setLastName(e.target.value)}
            />
            <TextField
              variant="outlined"
              margin="normal"
              required
              fullWidth
              name="password"
              label="Password"
              type="password"
              id="password"
              autoComplete="current-password"
              onChange={(e) => setPassword(e.target.value)}
            />
            <TextField
              variant="outlined"
              margin="normal"
              required
              fullWidth
              name="confirmPassword"
              label="Confirm Password"
              type="password"
              id="confirmPassword"
              autoComplete="current-password"
              onChange={(e) => setConfirmPassword(e.target.value)}
            />
            <FormControlLabel
              control={<Checkbox value="remember" color="primary" />}
              label="Remember me"
            />
            <Button
              type="submit"
              fullWidth
              variant="contained"
              color="primary"
              className={classes.submit}
              onClick={registerPost}
            >
              Register Now
            </Button>
            <Grid container>
              <Grid item>
                <Link href="/api/users/login" variant="body2">
                  Have an account? Login Now
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

type RegisterProps = WithStyles<typeof styles>;

export default withStyles(styles)(Register);
