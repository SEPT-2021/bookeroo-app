import React, { useEffect, useState } from "react";
import {
  Button,
  Container,
  createStyles,
  DialogTitle,
  Typography,
  withStyles,
  WithStyles,
} from "@material-ui/core";
import {
  Dialog,
  DialogActions,
  DialogContent,
  DialogContentText,
} from "@mui/material";
import { useMutation } from "react-query";
import Header from "../../components/Header";
import backgroundImage from "../../assets/books/backgroundBook2.png";
import Footer from "../../components/Footer";
import FormField from "../../util/FormField";
import { registerNewsletter } from "../../util/api";
import LoadingButton from "../../util/LoadingButton";

function HomePage({ classes }: HomePageProps) {
  const { mutate, error, isSuccess, isLoading } =
    useMutation(registerNewsletter);

  const [open, setOpen] = useState(false);
  const [email, setEmail] = useState("");
  const [errorMessage, setError] = useState<string>();
  const emailError = error?.response?.data.email;
  // Runs when the mutation has finished
  useEffect(() => {
    setError(emailError);
    setOpen(true);
  }, [emailError]);

  const handleClickOpen = () => {
    mutate({ email });
  };

  const handleClose = () => {
    setOpen(false);
    setError(undefined);
  };

  // eslint-disable-next-line consistent-return
  const renderPopup = () => {
    if (isSuccess)
      return (
        <Dialog
          open={open}
          onClose={handleClose}
          aria-labelledby="alert-dialog-title"
          aria-describedby="alert-dialog-description"
        >
          <DialogTitle id="alert-dialog-title">
            Thank you for signing up to our Newsletter :)
          </DialogTitle>
          <DialogContent>
            <DialogContentText id="alert-dialog-description">
              We have sent a email to your inbox to confirm your mail
              subscription!.
            </DialogContentText>
          </DialogContent>
          <DialogActions>
            <Button onClick={handleClose} autoFocus>
              Close
            </Button>
          </DialogActions>
        </Dialog>
      );
    if (error && errorMessage !== undefined)
      return (
        <Dialog
          open={open}
          onClose={handleClose}
          aria-labelledby="alert-dialog-title"
          aria-describedby="alert-dialog-description"
        >
          <DialogTitle id="alert-dialog-title">{errorMessage}</DialogTitle>
          <DialogActions>
            <Button onClick={handleClose} autoFocus>
              Close
            </Button>
          </DialogActions>
        </Dialog>
      );
  };

  return (
    <div className={classes.root}>
      <Header />
      <Container style={{ marginBottom: "200px" }} id="contact" maxWidth="sm">
        <Typography
          variant="h6"
          style={{ color: "white", fontWeight: "bolder" }}
        >
          Sign up for our Newsletter
        </Typography>
        <FormField
          name="email"
          autoComplete="email"
          placeholder="Email"
          style={{ backgroundColor: "white" }}
          onChange={setEmail}
        >
          Email
        </FormField>
        <LoadingButton
          loading={isLoading}
          variant="contained"
          color="primary"
          onClick={() => {
            handleClickOpen();
          }}
        >
          Submit
        </LoadingButton>
        {renderPopup()}
      </Container>
      <Footer />
    </div>
  );
}

const styles = () =>
  createStyles({
    root: {
      minHeight: "100vh",
      backgroundImage: `url(${process.env.PUBLIC_URL + backgroundImage})`,
      backgroundRepeat: "no-repeat",
      backgroundSize: "cover",
    },
  });

type HomePageProps = WithStyles<typeof styles>;

export default withStyles(styles)(HomePage);
