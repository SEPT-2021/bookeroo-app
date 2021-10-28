import React from "react";
import Animate from "@charlesvien/react-animatecss";
import { createStyles, withStyles, WithStyles } from "@material-ui/core";
import { Container, Typography } from "@mui/material";
import Link from "../../util/Link";
import img from "./img.png";

function AboutUs({ classes }: AboutUsProps) {
  return (
    <Container component="main" className={classes.root}>
      <Typography variant="h1">
        <Animate
          animationIn="fadeIn"
          animationOut="fadeOut"
          inDuration={4000}
          outDuration={2000}
          visible
        >
          Who Are We ?
        </Animate>
      </Typography>

      <Animate
        animationIn="fadeIn"
        animationOut="fadeOut"
        inDuration={2000}
        outDuration={3000}
        visible
      >
        <Typography variant="h6" sx={{ p: 1 }}>
          Bookaroo is an application developed to help anyone get online and
          sell books with ease. It's simple interface and colorful graphics make
          it easy for beginners and experienced users alike. The application
          also supports allowing users to leave reviews and choose which listing
          they want, either the cheapest one or from a well known seller.
          <br />
          Our goal is to make selling books as easy as possible, if you have any
          questions, don't hesitate to{" "}
          <Link style={{ fontSize: "1.2rem" }} to="/contact">
            contact us.
          </Link>
        </Typography>
        <h1>Our Team</h1>
        <div className="row">
          <div className="column">
            <div className="card">
              <div className="container">
                <h2>Shihab Sami</h2>
                <p>s3823710@student.rmit.edu.au</p>
              </div>
            </div>
          </div>

          <div className="column">
            <div className="card">
              <div className="container">
                <h2>Mathew Amos Woolley</h2>
                <p>s3908867@student.rmit.edu.au</p>
              </div>
            </div>
          </div>

          <div className="column">
            <div className="card">
              <div className="container">
                <h2>Ryan Dineth Samarakoon</h2>
                <p>s3844545@student.rmit.edu.au</p>
              </div>
            </div>
          </div>

          <div className="column">
            <div className="card">
              <div className="container">
                <h2>Andrew Nhan Trong Tran</h2>
                <p>s3785952@student.rmit.edu.au</p>
              </div>
            </div>
          </div>
          <p>This project was made for SEPT (COSC 2299/2428)</p>
        </div>
        <img src={img} alt="preview" style={{ width: "100%", marginTop: 50 }} />
      </Animate>
    </Container>
  );
}

const styles = () =>
  createStyles({
    root: {
      display: "flex",
      marginTop: 80,
      flexDirection: "column",
      alignItems: "center",
      justifyContent: "center",
      height: "90vh",
    },
  });

type AboutUsProps = WithStyles<typeof styles>;

export default withStyles(styles)(AboutUs);
