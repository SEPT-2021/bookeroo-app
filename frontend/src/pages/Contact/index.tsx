import React, { useState } from "react";
import {
  Button,
  Card,
  CardContent,
  createStyles,
  Grid,
  TextField,
  Typography,
  withStyles,
  WithStyles,
} from "@material-ui/core";

function Contact({ classes }: ContactProps) {
  const [isSubmitted, setSubmitted] = useState(false);
  return (
    <div className={classes.root}>
      <Grid>
        <Card style={{ maxWidth: 450, padding: "20px 5px", margin: "0 auto" }}>
          <CardContent>
            <Typography gutterBottom variant="h5">
              Contact Us
            </Typography>
            <Typography
              variant="body2"
              color="textSecondary"
              component="p"
              gutterBottom
            >
              Fill up the form and our team will get back to you within 24
              hours.
            </Typography>
            <form>
              <Grid container spacing={1}>
                <Grid xs={12} sm={6} item>
                  <TextField
                    placeholder="Enter first name"
                    label="First Name"
                    variant="outlined"
                    fullWidth
                    required
                  />
                </Grid>
                <Grid xs={12} sm={6} item>
                  <TextField
                    placeholder="Enter last name"
                    label="Last Name"
                    variant="outlined"
                    fullWidth
                    required
                  />
                </Grid>
                <Grid item xs={12}>
                  <TextField
                    type="email"
                    placeholder="Enter email"
                    label="Email"
                    variant="outlined"
                    fullWidth
                    required
                  />
                </Grid>
                <Grid item xs={12}>
                  <TextField
                    label="Message"
                    multiline
                    rows={4}
                    placeholder="Type your message here"
                    variant="outlined"
                    fullWidth
                    required
                  />
                </Grid>
                <Grid item xs={12}>
                  <Button
                    onClick={() => {
                      setSubmitted(true);
                      setTimeout(
                        () => window.open("mailto:hello@bookeroo.com.au"),
                        1000
                      );
                    }}
                    disabled={isSubmitted}
                    type="submit"
                    variant="contained"
                    color="primary"
                    fullWidth
                  >
                    {isSubmitted
                      ? "Done! We'll get back to you shortly"
                      : "Submit"}
                  </Button>
                </Grid>
              </Grid>
            </form>
          </CardContent>
        </Card>
      </Grid>
    </div>
  );
}

const styles = () => {
  return createStyles({
    root: {
      display: "flex",
      alignItems: "center",
      justifyContent: "center",
      marginTop: "120px",
      height: "100vh",
    },
  });
};

type ContactProps = WithStyles<typeof styles>;

export default withStyles(styles)(Contact);
