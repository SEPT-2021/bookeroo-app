import React from "react";
import { createStyles, withStyles, WithStyles } from "@material-ui/core";
import { Box } from "@mui/material";
import VerticalTabs from "../Sidebar";

function UserDashBoard({ classes }: UserDashBoardProps) {
  return (
    <Box sx={{ bgcolor: "background.paper" }}>
      <div className={classes.root}>
        <VerticalTabs />
      </div>
    </Box>
  );
}

const styles = () =>
  createStyles({
    root: {
      marginTop: "100px",
      display: "flex",
      justifyContent: "center",
      alignItems: "center",
      height: "70vh",
    },
  });

type UserDashBoardProps = WithStyles<typeof styles>;

export default withStyles(styles)(UserDashBoard);
