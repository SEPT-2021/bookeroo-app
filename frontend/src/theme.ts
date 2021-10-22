import { createTheme } from "@material-ui/core/styles";
import orange from "@material-ui/core/colors/orange";
import brown from "@material-ui/core/colors/brown";

export const DARK_MODE = false;

function invertWhiteBlack(str: "white" | "black") {
  if (!DARK_MODE) return str;
  return str === "white" ? "black" : "white";
}

// TODO produce a theme we like
const theme = createTheme({
  palette: {
    type: DARK_MODE ? "dark" : undefined,
    primary: {
      main: orange[500],
    },
    secondary: {
      main: brown[500],
    },
  },
  overrides: {
    MuiButton: {
      root: {
        borderRadius: 50,
      },
      outlinedSecondary: {
        "& span": {
          color: "black",
        },
      },
      label: {
        color: "white",
        textTransform: "none",
        fontWeight: "bold",
      },
      contained: {
        "& span": {
          color: "black",
        },
      },
      containedPrimary: {
        "& span": {
          color: "white",
        },
      },
      containedSecondary: {
        "& span": {
          color: "white",
        },
      },
      text: {
        "& span": {
          color: invertWhiteBlack("black"),
        },
      },
    },
  },
});
export default theme;
