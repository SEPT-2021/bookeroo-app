import * as React from "react";
import Button from "@mui/material/Button";
import { createStyles, Theme } from "@mui/material";
import { withStyles, WithStyles } from "@material-ui/core";
import DeleteIcon from "@mui/icons-material/Delete";
import { useMutation } from "react-query";
import { useHistory } from "react-router-dom";
import { useContext } from "react";
import { deleteUser } from "../../util/api";
import { GlobalContext } from "../../components/GlobalContext";

function DeleteUser({ classes }: DeleteAccountProps) {
  const { mutate } = useMutation(deleteUser);
  const { signOut } = useContext(GlobalContext);
  const history = useHistory();

  const deleteUsers = () => {
    mutate("");
    signOut();
    history.push("/");
  };

  return (
    <div className={classes.root}>
      <Button
        variant="outlined"
        startIcon={<DeleteIcon />}
        onClick={deleteUsers}
      >
        Delete Account
      </Button>
    </div>
  );
}

const styles = (theme: Theme) =>
  createStyles({
    root: {
      height: "30vh",
    },
    box: {
      "& > *": {
        margin: theme.spacing(1),
        width: "25ch",
      },
    },
  });

type DeleteAccountProps = WithStyles<typeof styles>;

export default withStyles(styles)(DeleteUser);
