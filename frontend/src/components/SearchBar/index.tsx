import React from "react";
import InputBase from "@material-ui/core/InputBase";
import {
  alpha,
  createStyles,
  makeStyles,
  Theme,
} from "@material-ui/core/styles";
import SearchIcon from "@material-ui/icons/Search";

const useStyles = makeStyles((theme: Theme) =>
  createStyles({
    search: {
      display: "flex",
      borderRadius: theme.shape.borderRadius,
      backgroundColor: theme.palette.grey["100"],
      marginTop: 15,
      "&:hover": {
        backgroundColor: alpha(theme.palette.common.white, 0.25),
      },
      marginLeft: 0,
      width: "100%",
      [theme.breakpoints.up("sm")]: {
        marginLeft: theme.spacing(1),
        width: "auto",
      },
    },
    searchIcon: {
      padding: theme.spacing(0, 2),
      height: "100%",
      pointerEvents: "none",
      display: "flex",
      alignItems: "center",
      justifyContent: "center",
    },
    inputRoot: {
      color: "inherit",
    },
    inputInput: {
      padding: theme.spacing(1, 1, 1, 0),
      // vertical padding + font size from searchIcon
      transition: theme.transitions.create("width"),
      width: "100%",
      [theme.breakpoints.up("sm")]: {
        width: "12ch",
        "&:focus": {
          width: "20ch",
        },
      },
    },
  })
);

export default function SearchBar({
  searchTerm,
  setSearchTerm,
  search,
}: {
  searchTerm: string;
  setSearchTerm: (val: string) => void;
  search(): void;
}) {
  const classes = useStyles();

  const handleKeyPress = async (event: { key: string }) => {
    if (event.key === "Enter") {
      search();
    }
  };

  const onInputChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    setSearchTerm(e.target.value);
    if (!e.target.value) {
      setTimeout(search, 200);
    }
  };

  return (
    <div className={classes.search}>
      <div className={classes.searchIcon}>
        <SearchIcon />
      </div>
      <InputBase
        type="search"
        placeholder="Search…"
        classes={{
          root: classes.inputRoot,
          input: classes.inputInput,
        }}
        inputProps={{ "aria-label": "search" }}
        value={searchTerm}
        onChange={onInputChange}
        onKeyPress={handleKeyPress}
      />
    </div>
  );
}
