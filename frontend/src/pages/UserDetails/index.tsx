import React, { useContext, useState } from "react";
import { Redirect } from "react-router-dom";
import {Person} from "@material-ui/icons";
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
import { GlobalContext } from "../../components/GlobalContext";
import theme from "../../theme";

function UserDetails({ classes }: UserDetailsProps) {
    const { user} = useContext(GlobalContext);

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
                        <Person />
                    </Avatar>
                    <Typography component="h1" variant="h5">
                        Your Details
                    </Typography>
                    <Box mt={2}>
                    <Typography component="h6" variant="h6">
                        First Name : {user?.firstName}
                    </Typography>
                    <Typography component="h6" variant="h6">
                        Last Name : {user?.lastName}
                    </Typography>
                    </Box>


                </div>
            </Grid>
        </Grid>
    );
}

const styles = () =>
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


type UserDetailsProps = WithStyles<typeof styles>;

export default withStyles(styles)(UserDetails);
