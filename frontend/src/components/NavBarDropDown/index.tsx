import React from "react";
import Menu from "@material-ui/core/Menu";
import MenuItem from "@material-ui/core/MenuItem";
import SortIcon from "@material-ui/icons/Sort";
import {
  createStyles,
  IconButton,
  WithStyles,
  withStyles,
} from "@material-ui/core";
import Link from "../../util/Link";

function NavBarDropDown({ classes }: NavBarDropDownProps) {
  const [anchorEl, setAnchorEl] = React.useState<null | HTMLElement>(null);

  const handleClick = (event: React.MouseEvent<HTMLButtonElement>) => {
    setAnchorEl(event.currentTarget);
  };

  const handleClose = () => {
    setAnchorEl(null);
  };

  return (
    <div>
      <IconButton
        aria-label="more"
        aria-controls="long-menu"
        aria-haspopup="true"
        onClick={handleClick}
      >
        <SortIcon className={classes.icon} />
      </IconButton>
      <Menu
        id="simple-menu"
        anchorEl={anchorEl}
        keepMounted
        open={Boolean(anchorEl)}
        onClose={handleClose}
      >
        <MenuItem onClick={handleClose}>
          <Link to="/login">Login</Link>
        </MenuItem>
        <MenuItem onClick={handleClose}>
          <Link to="/register">Register</Link>
        </MenuItem>
      </Menu>
    </div>
  );
}

const styles = () =>
  createStyles({
    icon: {
      color: "#F78888",
      fontSize: "2rem",
    },
  });

type NavBarDropDownProps = WithStyles<typeof styles>;

export default withStyles(styles)(NavBarDropDown);
