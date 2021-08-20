import React from "react";
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

function SignUp({ classes }: SignUpProps) {
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
                        SignUp
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
                        >
                            Sign-Up Now
                        </Button>
                        <Grid container>
                            <Grid item>
                                <Link href="/login" variant="body2">
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

type SignUpProps = WithStyles<typeof styles>;

export default withStyles(styles)(SignUp);
