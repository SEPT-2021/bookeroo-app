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
    Typography,
    withStyles,
    WithStyles,
} from "@material-ui/core";
import {updateUser} from "../../util/api";
import LoadingButton from "../../util/LoadingButton";
import FormField from "../../util/FormField";
import {GlobalContext} from "../../components/GlobalContext";
import theme from "../../theme";
import logo from "../../assets/logo.svg";

function UserDetails({ classes }: UserDetailsProps) {
    const {user} = useContext(GlobalContext);
    const { isLoading, mutate, isSuccess } = useMutation(updateUser);


    const [firstName, setFirstName] = useState("");
    const [lastName, setLastName] = useState("");
    const [addressLine1, setAddressLine1] = useState("");
    const [addressLine2, setAddressLine2] = useState("");
    const [city, setCity] = useState("");
    const [state, setState] = useState("");
    const [postalCode, setPostalCode] = useState("");
    const onSubmit = async () => {

        mutate({
            firstName,
            lastName,
            addressLine1,
            addressLine2,
            city,
            state,
            postalCode,
        });
    };

    if (isSuccess) {
        return <Redirect to="/UserDetails" />;
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
                        <Person />
                    </Avatar>

                    <Typography component="h1" variant="h5">
                        Your Details
                    </Typography>

                    <Box mt={2}>

                        <FormField
                            name="firstName"
                            label={user?.firstName}
                            defaultValue={user?.firstName}

                            autoComplete={user?.firstName}
                            onChange={setFirstName}

                        />
                        <FormField
                            name="lastName"
                            label={user?.lastName}
                            defaultValue={user?.lastName}


                            autoComplete={user?.lastName}
                            onChange={setLastName}
                        />
                        <FormField
                            name="addressLine1"
                            label={user?.addressLine1}
                            defaultValue={user?.addressLine1}


                            autoComplete={user?.addressLine1}
                            onChange={setAddressLine1}
                        />
                        <FormField
                            name="addressLine2"
                            label={user?.addressLine2}
                            defaultValue={user?.addressLine2}


                            autoComplete={user?.addressLine2}
                            onChange={setAddressLine2}
                        />
                        <FormField
                            name="city"
                            label={user?.city}
                            defaultValue={user?.city}


                            autoComplete={user?.city}
                            onChange={setCity}
                        />
                        <FormField
                            name="state"
                            label={user?.state}
                            defaultValue={user?.state}

                            autoComplete={user?.state}
                            onChange={setState}
                        />
                        <FormField
                            name="postalCode"
                            label={user?.postalCode}
                            defaultValue={user?.postalCode}


                            autoComplete={user?.postalCode}
                            onChange={setPostalCode}
                        />

                        <LoadingButton
                            loading={isLoading}
                            fullWidth
                            variant="contained"
                            color="primary"
                            className={classes.submit}
                            onClick={onSubmit}> update </LoadingButton>
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
