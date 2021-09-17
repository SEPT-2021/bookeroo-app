import React from "react";
import {
  Button,
  Container,
  createStyles,
  Typography,
  withStyles,
  WithStyles,
} from "@material-ui/core";
import Header from "../../components/Header";
import backgroundImage from "../../assets/books/backgroundBook2.png";
import Footer from "../../components/Footer";
import FormField from "../../util/FormField";

function HomePage({ classes }: HomePageProps) {
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
          placeholder="Email"
          style={{ backgroundColor: "white" }}
        >
          Email
        </FormField>
        <Button variant="contained" color="primary">
          Submit
        </Button>
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
